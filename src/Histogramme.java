/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;

/**
 *
 * @author Daniel
 */
public class Histogramme extends ShortPixmap {

    private final short[] gris;

    public Histogramme(Pixmap p) {
        super(p);
        gris = new short[256];
        for (int i = 0; i < data.length; i++) {
            gris[data[i]]++;
        }
    }

    public short getHistogramme(int i) {
        return gris[i];
    }

    public void filtreMedian() { // on peut mettre le nombre de cases en param
        short[] v = new short[9];
        short r = 0;
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                r = 0;
                for (int k = i - 1; k < i + 2; k++) {
                    for (int l = j - 1; l < j + 2; l++) {
                        v[r++] = data[l * width + k];
                    }
                }
                data[j * width + i] = valeurMediane(v);
            }
        }
    }

    public short valeurMediane(short[] v) {
        Arrays.sort(v);
        return v[4];
        /* on prend la 5e valeur du vecteur car notre vecteur contient ici 
         toujours 9 valeurs */
    }

    public void filtreNagao() {
        int pos;
        for (int i = 2; i < width - 2; i++) {
            for (int j = 2; j < height - 2; j++) {
                // utiliser les 3 petits points de java
                pos = 0;
                // on stocke les 25 pixels concernés
                short[] v = new short[25];
                for (int k = i - 2; k < i + 3; ++k) {
                    for (int l = j - 2; l < j + 3; ++l) {
                        v[pos++] = data[l * width + k];
                    }
                }

                // tableaux contenant les moyennes et variances de chaque fenêtre
                double[] moyennes = new double[9];
                double[] variances = new double[9];
                // fenêtres de Nagao
                short[] f1 = {v[0], v[1], v[5], v[6], v[7], v[11], v[12]};
                moyennes[0] = getMean(f1); variances[0] = getVariance(f1);
                short[] f2 = {v[1], v[2], v[3], v[6], v[7], v[8], v[12]};
                moyennes[1] = getMean(f1); variances[1] = getVariance(f1);
                short[] f3 = {v[3], v[4], v[7], v[8], v[9], v[12], v[13]};
                moyennes[2] = getMean(f1); variances[2] = getVariance(f1);
                short[] f4 = {v[5], v[6], v[10], v[11], v[12], v[15], v[16]};
                moyennes[3] = getMean(f1); variances[3] = getVariance(f1);
                short[] f5 = {v[8], v[9], v[12], v[13], v[14], v[18], v[19]};
                moyennes[4] = getMean(f1); variances[4] = getVariance(f1);
                short[] f6 = {v[6], v[7], v[8], v[11], v[12], v[13], v[16], v[17], v[18]};
                moyennes[5] = getMean(f1); variances[5] = getVariance(f1);
                short[] f7 = {v[11], v[12], v[15], v[16], v[17], v[20], v[21]};
                moyennes[6] = getMean(f1); variances[6] = getVariance(f1);
                short[] f8 = {v[12], v[16], v[17], v[18], v[21], v[22], v[23]};
                moyennes[7] = getMean(f1); variances[7] = getVariance(f1);
                short[] f9 = {v[12], v[13], v[17], v[18], v[19], v[23], v[24]};
                moyennes[8] = getMean(f1); variances[8] = getVariance(f1);

                // on récupère l'indice de la plus petite variance
                int indice = 0;
                for (int m = 1; m < 9; ++m) {
                    if (variances[m] < variances[indice])
                        indice = m;
                }

                data[j * width + i] = (short) moyennes[indice];
            }
        }
    }

    public double getMean(short[] v) {
        double sum = 0.0;
        for (short a : v) {
            sum += a;
        }
        return sum / v.length;
    }

    public double getVariance(short[] v) {
        double mean = getMean(v);
        double temp = 0;
        for (short a : v) {
            temp += (a - mean) * (a - mean);
        }
        return temp / v.length;
    }
}
