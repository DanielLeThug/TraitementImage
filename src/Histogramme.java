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
        for (int i = 2; i < width - 2; i++) {
            for (int j = 2; j < height - 2; j++) {
                // utiliser les 3 petits points de java
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