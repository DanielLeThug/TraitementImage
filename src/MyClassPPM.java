/**
 * Created by Simon on 27/03/2017.
 */
public class MyClassPPM {

    public static ByteRGBPixmap binarisation(ByteRGBPixmap bp) {
        return new ByteRGBPixmap(bp.width, bp.height, Pixmap.getBytes(MyClassPGM.binarisation(bp.r.getShorts())), Pixmap.getBytes(MyClassPGM.binarisation(bp.g.getShorts())), Pixmap.getBytes(MyClassPGM.binarisation(bp.b.getShorts())));
    }


}
