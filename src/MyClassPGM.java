
import java.util.Arrays;

/**
 * Created by Simon on 18/03/2017.
 */
public class MyClassPGM {

    public static double getOtsuThreshold(ShortPixmap sp) {

        short[] n = getHistogramme(sp);
        double[] p = getProbabilities(n, sp.data.length);
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

    public static short[] getHistogramme(ShortPixmap sp) {
        short[] hist = new short[256];

        for (int i = 0; i < sp.data.length; i++) {
            hist[sp.data[i]]++;
        }
        return hist;
    }

    private static double[] getProbabilities(short[] histogram, int totalPixels) {

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

    public static ShortPixmap etirementHisto(ShortPixmap sp) {
        short[] hist = getHistogramme(sp);
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
        for (int k = 0; k < sp.size; k++) {
            sp.data[k] = (short) ((255 / (max - min)) * (sp.data[k] - min));
        }
        return sp;
    }

    public static ShortPixmap egalisationHisto(ShortPixmap sp) {
        short[] hist = getHistogramme(sp);
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
        short[] hist1 = getHistogramme(spToModif);
        short[] hist2 = getHistogramme(sp);
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

}
