/**
 * Created by Simon on 27/03/2017.
 */
public class MyClassPPM {
    
    // Fonction qui applique l'algorithme d'Otsu sur les 3 composantes r, g et b pour binariser l'image.
    public static ByteRGBPixmap binarisation(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.binarisation(bp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.binarisation(bp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.binarisation(bp.b.getShorts())));
    }
    
    // Fonction qui applique l'algorithme d'étirement d'histogramme sur les 3 composantes r, g et b pour étirer l'image.
    public static ByteRGBPixmap etirementHisto(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.etirementHisto(bp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.etirementHisto(bp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.etirementHisto(bp.b.getShorts())));
    }

    // Fonction qui applique l'algorithme d'égalisation d'histogramme sur les 3 composantes r, g et b pour égaliser l'image.
    public static ByteRGBPixmap egalisationHisto(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.egalisationHisto(bp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.egalisationHisto(bp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.egalisationHisto(bp.b.getShorts())));
    }

    // Fonction qui applique l'algorithme de spécification d'histogramme sur les 3 composantes r, g et b pour spécifier l'image.
    public static ByteRGBPixmap specificationHisto(ByteRGBPixmap bp, ByteRGBPixmap tmp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.specificationHisto(bp.r.getShorts(), tmp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.specificationHisto(bp.g.getShorts(), tmp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.specificationHisto(bp.b.getShorts(), tmp.b.getShorts())));
    }

    // Fonction qui applique l'algorithme de filtre médian sur les 3 composantes r, g et b pour réduire le bruit de l'image.
    public static ByteRGBPixmap filtreMedian(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.filtreMedian(bp.width, bp.height, bp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.filtreMedian(bp.width, bp.height, bp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.filtreMedian(bp.width, bp.height, bp.b.getShorts())));
    }

    // Fonction qui applique l'algorithme du filtre de Nagao sur les 3 composantes r, g et b pour lisser les contours de l'image.
    public static ByteRGBPixmap filtreNagao(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.filtreNagao(bp.width, bp.height, bp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.filtreNagao(bp.width, bp.height, bp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.filtreNagao(bp.width, bp.height, bp.b.getShorts())));
    }
    
    //Fonction qui applique l'opération logique ET sur les 3 composantes r, g et b par rapport à une autre image de même taille.
    public static ByteRGBPixmap and(ByteRGBPixmap bp, ByteRGBPixmap tmp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.and(bp.r.getShorts(), tmp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.and(bp.g.getShorts(), tmp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.and(bp.b.getShorts(), tmp.b.getShorts())));
    }
    
    //Fonction qui applique l'opération logique AND sur les 3 composantes r, g et b par rapport à une autre image de même taille.
    public static ByteRGBPixmap or(ByteRGBPixmap bp, ByteRGBPixmap tmp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.or(bp.r.getShorts(), tmp.r.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.or(bp.g.getShorts(), tmp.g.getShorts())), 
                                                      Pixmap.getBytes(MyClassPGM.or(bp.b.getShorts(), tmp.b.getShorts())));
    }
}
