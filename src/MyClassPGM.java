import java.util.Arrays;

/**
 * Created by Simon on 18/03/2017.
 */


public class MyClassPGM {

    public static short[] getHistogramme(ShortPixmap sp) {
        short[] hist = new short[256];
        for (int i = 0; i < sp.data.length; i++) {
            hist[sp.data[i]]++;
        }

        return hist;
    }

    public static ShortPixmap etirementHisto(ShortPixmap sp) {
        short[] hist = getHistogramme(sp);
        double max,min;

        short i=0;
        while(hist[i] == 0) {
            i++;
        }
        min=i;
        i=255;
        while(hist[i] == 0) {
            i--;
        }
        max=i;
        for(int k =0 ; k < sp.size ; k++) {
            sp.data[k] = (short) ((255/(max-min))*(sp.data[k]-min));
        }
        return sp;
    }

    public static ShortPixmap egalisationHisto(ShortPixmap sp) {
        short[] hist = getHistogramme(sp);
        double[] hist2 = new double[256];
        double tmp = 0;
        for(int i = 0 ; i < 256 ; i++) {
            tmp += (double)hist[i]/(double)sp.size;
            hist2[i] = tmp;
        }
        for(int i = 0 ; i < sp.size ; i++) {
            sp.data[i] = (short) (hist2[sp.data[i]]*255);
        }
        return sp;
    }

    public static ShortPixmap specificationHisto(ShortPixmap spToModif, ShortPixmap sp) {
        short[] hist1 = getHistogramme(spToModif);
        short[] hist2 = getHistogramme(sp);
        double[] hist3 = new double[256];
        double[] hist4 = new double[256];
        double tmp1=0.0, tmp2=0.0;
        for(int i = 0 ; i < 256 ; i++) {
            tmp1 += (double)hist1[i]/(double)spToModif.size;
            hist3[i] = tmp1;
            tmp2 += (double)hist2[i]/(double)sp.size;
            hist4[i] = tmp2;
        }

        double dif = 0.0, dif2 = 0.0;
        double val;
        for(int i = 0 ; i < spToModif.size ; i++) {
            short j=0;
            val = hist3[spToModif.data[i]];
            while (j < 256 && hist4[j] == 0.0)
                j++;
            dif = val - hist4[j];
            while (j+1 < 256 && hist4[j] == hist4[j+1])
                j++;
            j++;
            dif2 = val - hist4[j];

            while(Math.abs(dif) > Math.abs(dif2)) {
                dif = dif2;
                while (j+1 < 256 && hist4[j] == hist4[j+1])
                    j++;
                dif2 = val - hist4[j];
            }
            spToModif.data[i] = j;
        }
        return spToModif;
    }

    public static ShortPixmap filtreMedian(ShortPixmap sp) { // on peut mettre le nombre de cases en param
        short[] v = new short[9];
        short r = 0;
        for (int i = 1; i < sp.width - 1; i++) {
            for (int j = 1; j < sp.height - 1; j++) {
                r = 0;
                for (int k = i - 1; k < i + 2; k++) {
                    for (int l = j - 1; l < j + 2; l++) {
                        v[r++] = sp.data[l * sp.width + k];
                    }
                }
                sp.data[j * sp.width + i] = valeurMediane(v);
            }
        }
        return sp;
    }

    public static short valeurMediane(short[] v) {
        Arrays.sort(v);
        return v[4];
        /* on prend la 5e valeur du vecteur car notre vecteur contient ici
         toujours 9 valeurs */
    }

}
