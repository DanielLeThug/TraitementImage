package traitementimages;

import java.io.*;

public class DoublePixmap extends Pixmap {

    public final double[] data;

    public DoublePixmap(int w, int h, double[] pixels) throws IllegalArgumentException {
        super(w, h);
        if (pixels == null) {
            pixels = new double[w * h];
        }
        if (pixels.length != w * h) {
            throw new IllegalArgumentException("bad dimensions");
        }
        data = pixels;
    }

    public DoublePixmap(int w, int h) {
        this(w, h, null);
    }

    public DoublePixmap(Pixmap p) {
        this(p.width, p.height, p.getDoubles());
    }

    public DoublePixmap(String fileName) throws IOException {
        super(fileName);
        data = getDoubles(readBytes());
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
        return "double";
    }

}
