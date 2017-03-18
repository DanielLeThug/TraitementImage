/**
 * Created by Simon on 18/03/2017.
 */


public class MyClassPGM {

    public static short[] getHistogramme(ShortPixmap sp) {
        short[] hist = new short[256];
        for (int i = 0; i < sp.data.length; i++) {
            hist[sp.data[i]]++;
        }

        return hist;
    }

    public static void etirementHisto(ShortPixmap sp, String file) {
        ShortPixmap csp = new ShortPixmap(sp); //Clonage pour ne pas modif l'image initial
        short[] hist = getHistogramme(csp);
        double max,min;

        short i=0;
        while(hist[i] == 0) {
            i++;
        }
        min=i;
        i=255;
        while(hist[i] == 0) {
            i--;
        }
        max=i;
        for(int k =0 ; k < csp.size ; k++) {
            csp.data[k] = (short) ((255/(max-min))*(sp.data[k]-min));
        }
        csp.write(file);
    }

    public static void egalisationHisto(ShortPixmap sp, String file) {
        ShortPixmap csp = new ShortPixmap(sp); //Clonage pour ne pas modif l'image initial
        short[] hist = getHistogramme(csp);
        double[] hist2 = new double[256];
        double tmp = 0;
        for(int i = 0 ; i < 256 ; i++) {
            tmp += (double)hist[i]/(double)sp.size;
            hist2[i] = tmp;
        }
        for(int i = 0 ; i < csp.size ; i++) {
            csp.data[i] = (short) (hist2[csp.data[i]]*255);
        }
        csp.write(file);
    }
}
