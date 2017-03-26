/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class Main {
    
    public static void main(String[] args) {
        // test de lecture et d'écriture d'un fichier pgm
        ShortPixmap sp1;
        //ShortPixmap sp2;
        try {
            sp1 = new ShortPixmap("aerial1.pgm");
            //sp2 = new ShortPixmap("cuadrado3.pgm");
        } catch (IOException e) {
            sp1 = null;
            //sp2 = null
            System.exit(0);
        }
        //MyClassPGM.etirementHisto(sp1,"resultatEtirement.pgm");
        //MyClassPGM.egalisationHisto(sp1,"resultatEgalisation.pgm");
        //MyClassPGM.specificationHisto(sp1, sp2, "resultatSpecification.pgm");
        MyInterfaceGraphique myInterfaceGraphique = new MyInterfaceGraphique();
    }
}
