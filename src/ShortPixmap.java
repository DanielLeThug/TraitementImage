import java.io.*;

public class ShortPixmap extends Pixmap {

    public final short[] data;

    public ShortPixmap(int w, int h, short[] pixels) throws IllegalArgumentException {
        super(w, h);
        if (pixels == null) {
            pixels = new short[w * h];
        }
        if (pixels.length != w * h) {
            throw new IllegalArgumentException("bad dimensions");
        }
        data = pixels;
    }

    public ShortPixmap(int w, int h) {
        this(w, h, null);
    }

    public ShortPixmap(Pixmap p) {
        this(p.width, p.height, p.getShorts());
    }

    public ShortPixmap(String fileName) throws IOException {
        super(fileName);
        data = getShorts(readBytes());
        close();
    }

    // conversions
    @Override
    public byte[] getBytes() {
        return getBytes(data);
    }

    @Override
    public short[] getShorts() {
        return getShorts(data);
    }

    @Override
    public double[] getDoubles() {
        return getDoubles(data);
    }

    // strings
    @Override
    public String pixelType() {
        return "short";
    }

}
