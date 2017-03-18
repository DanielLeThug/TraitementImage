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
        ShortPixmap csp = new ShortPixmap(sp); //Clonage pour ne pas modif l'image initial #Je sais pas si c'est utile
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

    public static void specificationHisto(ShortPixmap spToModif, ShortPixmap sp, String file) {
        short[] hist1 = getHistogramme(spToModif);
        short[] hist2 = getHistogramme(sp);
        double[] hist3 = new double[256];
        double[] hist4 = new double[256];
        double tmp1=0.0, tmp2=0.0;
        for(int i = 0 ; i < 256 ; i++) {
            tmp1 += (double)hist1[i]/(double)spToModif.size;
            hist3[i] = tmp1;
            tmp2 += (double)hist2[i]/(double)sp.size;
            hist4[i] = tmp2;
        }

        double dif = 0.0, dif2 = 0.0;
        double val;
        for(int i = 0 ; i < spToModif.size ; i++) {
            short j=0;
            val = hist3[spToModif.data[i]];
            while (j < 256 && hist4[j] == 0.0)
                j++;
            dif = val - hist4[j];
            while (j+1 < 256 && hist4[j] == hist4[j+1])
                j++;
            j++;
            dif2 = val - hist4[j];

            while(Math.abs(dif) > Math.abs(dif2)) {
                dif = dif2;
                while (j+1 < 256 && hist4[j] == hist4[j+1])
                    j++;
                dif2 = val - hist4[j];
            }
            spToModif.data[i] = j;
        }

        spToModif.write(file);
    }
}
