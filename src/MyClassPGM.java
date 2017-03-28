
import java.util.Arrays;

/**
 * Created by Simon on 18/03/2017.
 */
public class MyClassPGM {

    public static short[] binarisation(short[] data) {
        double t = getOtsuThreshold(data);
        for (int i = 0; i < data.length; i++) {
            if (data[i] <= t) {
                data[i] = 0;
            } else {
                data[i] = 255;
            }
        }
        return data;
    }

    public static double getOtsuThreshold(short[] data) {

        long[] n = getHistogramme(data);
        double[] p = getProbabilities(n, data.length);
        double[] Wo = getWo(p);
        double W = getW(p);
        double[] W1 = getW1(Wo, W);
        double UT = getUT(p);
        double[] Ut = getUt(p);
        double[] Uo = getUo(Ut, Wo);
        double[] U1 = getU1(UT, Ut, Uo);
        double sigmaSqrT = getSigmaSqrT(UT, p);
        double[] sigmaSqrBt = getSigmaSqrBt(Wo, W1, U1, Uo);
        double[] eta = getEta(sigmaSqrBt, sigmaSqrT);
        return getMaxIndex(eta);

    }

    public static long[] getHistogramme(short[] data) {
        long[] hist = new long[256];

        for (int i = 0; i < data.length; i++) {
            hist[data[i]]++;
        }

        return hist;
    }

    private static double[] getProbabilities(long[] histogram, int totalPixels) {

        double[] probability = new double[histogram.length];

        for (int index = 0; index < probability.length; index++) {
            probability[index] = ((double) histogram[index]) / ((double) totalPixels);
        }

        return probability;
    }

    private static double[] getWo(double[] probability) {

        double[] Wo = new double[probability.length];
        Wo[0] = probability[0];

        for (int index = 1; index < Wo.length; index++) {
            Wo[index] = Wo[index - 1] + probability[index];
        }
        return Wo;
    }

    private static double getW(double[] probability) {

        double W = 0;

        for (int index = 0; index < probability.length; index++) {
            W += probability[index];
        }
        return W;
    }

    private static double[] getW1(double[] Wo, double W) {

        double[] W1 = new double[Wo.length];

        for (int index = 0; index < W1.length; index++) {
            W1[index] = W - Wo[index];
        }

        return W1;
    }

    private static double getUT(double[] probability) {

        double UT = 0;

        for (int index = 0; index < probability.length; index++) {
            UT += (((double) index) * probability[index]);
        }
        return UT;

    }

    private static double[] getUt(double[] probability) {

        double[] Ut = new double[probability.length];

        Ut[0] = 0;
        for (int index = 1; index < probability.length; index++) {
            Ut[index] = Ut[index - 1] + (((double) index) * probability[index]);
        }

        return Ut;
    }

    private static double[] getUo(double[] Ut, double[] Wo) {

        double[] Uo = new double[Ut.length];

        for (int index = 0; index < Ut.length; index++) {
            if (Wo[index] == 0.0) {
                Uo[index] = 0.0;
            } else {
                Uo[index] = Ut[index] / Wo[index];
            }
        }
        return Uo;

    }

    private static double[] getU1(double UT, double[] Ut, double[] Uo) {

        double[] U1 = new double[Ut.length];

        for (int index = 0; index < U1.length; index++) {
            if (Uo[index] == 1.0) {
                Uo[index] = 1.1;
            } else {
                U1[index] = (UT - Ut[index]) / (1 - Uo[index]);
            }
        }

        return U1;

    }

    private static double getSigmaSqrT(double UT, double[] probability) {

        double sigmaSqrT = 0;

        for (int index = 0; index < probability.length; index++) {
            sigmaSqrT += (Math.pow((index - UT), 2) * probability[index]);
        }
        return sigmaSqrT;

    }

    private static double[] getSigmaSqrBt(double[] Wo, double[] W1, double[] U1, double[] Uo) {
        double sigmaSqrBt[] = new double[Wo.length];

        for (int index = 0; index < sigmaSqrBt.length; index++) {
            sigmaSqrBt[index] = Wo[index] * W1[index] * Math.pow((U1[index] - Uo[index]), 2);
        }

        return sigmaSqrBt;
    }

    private static int getMaxIndex(double[] array) {

        int maxIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[maxIndex] < array[i]) {
                maxIndex = i;
            }
        }
        return maxIndex;

    }

    private static double[] getEta(double[] sigmaSqrBt, double sigmaSqrT) {
        double eta[] = new double[sigmaSqrBt.length];
        for (int index = 0; index < sigmaSqrBt.length; index++) {
            eta[index] = sigmaSqrBt[index] / sigmaSqrT;
        }
        return eta;
    }

    public static short[] etirementHisto(short[] data) {
        long[] hist = getHistogramme(data);
        double max, min;

        short i = 0;
        while (hist[i] == 0) {
            i++;
        }
        min = i;
        i = 255;
        while (hist[i] == 0) {
            i--;
        }
        max = i;
        for (int k = 0; k < data.length; k++) {
            data[k] = (short) ((255 / (max - min)) * (data[k] - min));
        }
        return data;
    }

    public static ShortPixmap egalisationHisto(ShortPixmap sp) {
        long[] hist = getHistogramme(sp.data);
        double[] hist2 = new double[256];
        double tmp = 0;
        for (int i = 0; i < 256; i++) {
            tmp += (double) hist[i] / (double) sp.size;
            hist2[i] = tmp;
        }
        for (int i = 0; i < sp.size; i++) {
            sp.data[i] = (short) (hist2[sp.data[i]] * 255);
        }
        return sp;
    }

    public static ShortPixmap specificationHisto(ShortPixmap spToModif, ShortPixmap sp) {
        long[] hist1 = getHistogramme(spToModif.data);
        long[] hist2 = getHistogramme(sp.data);
        double[] hist3 = new double[256];
        double[] hist4 = new double[256];
        double tmp1 = 0.0, tmp2 = 0.0;
        for (int i = 0; i < 256; i++) {
            tmp1 += (double) hist1[i] / (double) spToModif.size;
            hist3[i] = tmp1;
            tmp2 += (double) hist2[i] / (double) sp.size;
            hist4[i] = tmp2;
        }

        double dif = 0.0, dif2 = 0.0;
        double val;
        for (int i = 0; i < spToModif.size; i++) {
            short j = 0;
            val = hist3[spToModif.data[i]];
            while (j < 256 && hist4[j] == 0.0) {
                j++;
            }
            dif = val - hist4[j];
            while (j + 1 < 256 && hist4[j] == hist4[j + 1]) {
                j++;
            }
            j++;
            dif2 = val - hist4[j];

            while (Math.abs(dif) > Math.abs(dif2)) {
                dif = dif2;
                while (j + 1 < 256 && hist4[j] == hist4[j + 1]) {
                    j++;
                }
                dif2 = val - hist4[j];
            }
            spToModif.data[i] = j;
        }
        return spToModif;
    }

    public static ShortPixmap filtreMedian(ShortPixmap sp) { // on peut mettre le nombre de cases en param (carré de 3pix par défaut mais pourrait être 5/7/etc
        ShortPixmap newSP = new ShortPixmap(sp);
        short[] v = new short[9];
        short r = 0;
        for (int i = 1; i < newSP.width - 1; i++) {
            for (int j = 1; j < newSP.height - 1; j++) {
                r = 0;
                for (int k = i - 1; k < i + 2; k++) {
                    for (int l = j - 1; l < j + 2; l++) {
                        v[r++] = sp.data[l * sp.width + k];
                    }
                }
                newSP.data[j * newSP.width + i] = valeurMediane(v);
            }
        }
        return newSP;
    }

    public static short valeurMediane(short[] v) {
        Arrays.sort(v);
        return v[4];
        /* on prend la 5e valeur du vecteur car notre vecteur contient ici
         toujours 9 valeurs */
    }

    public static ShortPixmap filtreNagao(ShortPixmap sp) {
        ShortPixmap ret = new ShortPixmap(sp);
        int pos;
        for (int i = 2; i < sp.width - 2; i++) {
            for (int j = 2; j < sp.height - 2; j++) {
                // utiliser les 3 petits points de java
                pos = 0;
                // on stocke les 25 pixels concernés
                short[] v = new short[25];
                for (int k = i - 2; k < i + 3; ++k) {
                    for (int l = j - 2; l < j + 3; ++l) {
                        v[pos++] = sp.data[l * sp.width + k];
                    }
                }

                // tableaux contenant les moyennes et variances de chaque fenêtre
                double[] moyennes = new double[9];
                double[] variances = new double[9];
                // fenêtres de Nagao
                short[] f1 = {v[0], v[1], v[5], v[6], v[7], v[11], v[12]};
                moyennes[0] = getMean(f1); variances[0] = getVariance(f1);
                short[] f2 = {v[1], v[2], v[3], v[6], v[7], v[8], v[12]};
                moyennes[1] = getMean(f2); variances[1] = getVariance(f2);
                short[] f3 = {v[3], v[4], v[7], v[8], v[9], v[12], v[13]};
                moyennes[2] = getMean(f3); variances[2] = getVariance(f3);
                short[] f4 = {v[5], v[6], v[10], v[11], v[12], v[15], v[16]};
                moyennes[3] = getMean(f4); variances[3] = getVariance(f4);
                short[] f5 = {v[8], v[9], v[12], v[13], v[14], v[18], v[19]};
                moyennes[4] = getMean(f5); variances[4] = getVariance(f5);
                short[] f6 = {v[6], v[7], v[8], v[11], v[12], v[13], v[16], v[17], v[18]};
                moyennes[5] = getMean(f6); variances[5] = getVariance(f6);
                short[] f7 = {v[11], v[12], v[15], v[16], v[17], v[20], v[21]};
                moyennes[6] = getMean(f7); variances[6] = getVariance(f7);
                short[] f8 = {v[12], v[16], v[17], v[18], v[21], v[22], v[23]};
                moyennes[7] = getMean(f8); variances[7] = getVariance(f8);
                short[] f9 = {v[12], v[13], v[17], v[18], v[19], v[23], v[24]};
                moyennes[8] = getMean(f9); variances[8] = getVariance(f9);

                // on récupère l'indice de la plus petite variance
                int indice = 0;
                for (int m = 1; m < 9; ++m) {
                    if (variances[m] < variances[indice])
                        indice = m;
                }

                ret.data[j * sp.width + i] = (short) moyennes[indice];
            }
        }
        return ret;
    }

    public static double getMean(short[] v) {
        double sum = 0.0;
        for (short a : v) {
            sum += a;
        }
        return sum / v.length;
    }

    public static double getVariance(short[] v) {
        double mean = getMean(v);
        double temp = 0;
        for (short a : v) {
            temp += (a - mean) * (a - mean);
        }
        return temp / v.length;
    }

}
