/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitementimages;

import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class Main {
    
    public static void main(String[] args) {
        // test de lecture et d'écriture d'un fichier pgm
        ShortPixmap p1;
        try {
            p1 = new ShortPixmap("aerial1.pgm");
        } catch (IOException e) {
            p1 = null;
            System.exit(0);
        }
        p1.write("test.pgm");

        // test de lecture et d'écriture d'un fichier ppm
        ByteRGBPixmap rgb1;
        try {
            rgb1 = new ByteRGBPixmap("Lena.512.ppm");
        } catch (IOException e) {
            rgb1 = null;
            System.exit(0);
        }
        rgb1.write("test.ppm");

        // test histogramme
        Histogramme h1 = new Histogramme(p1);
        h1.filtreMedian();
        h1.write("resultatMedian.pgm");
    }
}
