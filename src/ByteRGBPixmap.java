import java.io.*;

public class ByteRGBPixmap extends RGBPixmap {

    public final BytePixmap r;
    public final BytePixmap g;
    public final BytePixmap b;

    public ByteRGBPixmap(int w, int h, byte[] rPixels, byte[] gPixels, byte[] bPixels) throws IllegalArgumentException {
        super(w, h);
        r = new BytePixmap(width, height, rPixels);
        g = new BytePixmap(width, height, gPixels);
        b = new BytePixmap(width, height, bPixels);
    }

    public ByteRGBPixmap(int w, int h) {
        this(w, h, null, null, null);
    }

    public ByteRGBPixmap(Pixmap r, Pixmap g, Pixmap b) {
        this(r.width, r.height, r.getBytes(), g.getBytes(), b.getBytes());
    }

    public ByteRGBPixmap(RGBPixmap p) {
        this(p.getR(), p.getG(), p.getB());
    }

    public ByteRGBPixmap(String fileName) throws IOException {
        super(fileName);
        r = new BytePixmap(width, height, getChunky(0));
        g = new BytePixmap(width, height, getChunky(1));
        b = new BytePixmap(width, height, getChunky(2));
        closeRGB();
    }

    // conversions
    @Override
    public Pixmap getR() {
        return r;
    }

    @Override
    public Pixmap getG() {
        return g;
    }

    @Override
    public Pixmap getB() {
        return b;
    }

    // strings
    @Override
    public String pixelType() {
        return "byte*3";
    }

}
