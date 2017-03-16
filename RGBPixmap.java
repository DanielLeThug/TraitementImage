package traitementimages;

import java.io.*;

public abstract class RGBPixmap extends Pixmap {

    private static String MAGIC_PPM = "P6\n";
    private byte[] data;

    public RGBPixmap(int w, int h) {
        super(w, h);
    }

    public RGBPixmap(String fileName) throws IOException {
        super(fileName, MAGIC_PPM);
        data = readBytes(3 * size);
    }

    byte[] getChunky(int first) {
        if (data == null) {
            return null;
        }
        byte[] buffer = new byte[size];
        for (int i = first, j = 0; i < data.length; i += 3, j++) {
            buffer[j] = data[i];
        }
        return buffer;
    }

    final void closeRGB() {
        close();
        data = null;
    }

    private byte[] makeBytes() {
        byte[] r = getR().getBytes();
        byte[] g = getG().getBytes();
        byte[] b = getB().getBytes();
        byte[] buffer = new byte[3 * size];
        for (int i = 0, j = 0; i < r.length; i++) {
            buffer[j++] = r[i];
            buffer[j++] = g[i];
            buffer[j++] = b[i];
        }
        return buffer;
    }

    @Override
    public void write(String fileName) {
        write(fileName, MAGIC_PPM, makeBytes());
    }

    // conversions
    public abstract Pixmap getR();

    public abstract Pixmap getG();

    public abstract Pixmap getB();

    @Override
    public byte[] getBytes() {
        return null;
    }

    @Override
    public short[] getShorts() {
        return null;
    }

    @Override
    public double[] getDoubles() {
        return null;
    }

}
