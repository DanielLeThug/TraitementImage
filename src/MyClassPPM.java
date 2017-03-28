/**
 * Created by Simon on 27/03/2017.
 */
public class MyClassPPM {

    public static ByteRGBPixmap binarisation(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.binarisation(bp.r.getShorts())), Pixmap.getBytes(MyClassPGM.binarisation(bp.g.getShorts())), Pixmap.getBytes(MyClassPGM.binarisation(bp.b.getShorts())));
    }

    public static ByteRGBPixmap etirementHisto(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.etirementHisto(bp.r.getShorts())), Pixmap.getBytes(MyClassPGM.etirementHisto(bp.g.getShorts())), Pixmap.getBytes(MyClassPGM.etirementHisto(bp.b.getShorts())));
    }

    public static ByteRGBPixmap egalisationHisto(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.egalisationHisto(bp.r.getShorts())), Pixmap.getBytes(MyClassPGM.egalisationHisto(bp.g.getShorts())), Pixmap.getBytes(MyClassPGM.egalisationHisto(bp.b.getShorts())));
    }

    public static ByteRGBPixmap specificationHisto(ByteRGBPixmap bp, ByteRGBPixmap tmp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.specificationHisto(bp.r.getShorts(), tmp.r.getShorts())), Pixmap.getBytes(MyClassPGM.specificationHisto(bp.g.getShorts(), tmp.g.getShorts())), Pixmap.getBytes(MyClassPGM.specificationHisto(bp.b.getShorts(), tmp.b.getShorts())));
    }

    public static ByteRGBPixmap filtreMedian(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.filtreMedian(bp.width, bp.height, bp.r.getShorts())), Pixmap.getBytes(MyClassPGM.filtreMedian(bp.width, bp.height, bp.g.getShorts())), Pixmap.getBytes(MyClassPGM.filtreMedian(bp.width, bp.height, bp.b.getShorts())));
    }


}
