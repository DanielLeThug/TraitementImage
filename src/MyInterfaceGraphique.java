import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Simon on 21/03/2017.
 */

public class MyInterfaceGraphique extends JFrame {

    private ShortPixmap pgm = null;
    private ByteRGBPixmap ppm = null;

    private BufferedImage bufferImage(ShortPixmap pgm) {
        BufferedImage img = new BufferedImage(pgm.width, pgm.height,BufferedImage.TYPE_INT_RGB);
        for(int i = 0 ; i < pgm.width ; i++) {
            for(int j = 0 ; j < pgm.height ; j++) {
                short pix = pgm.data[j * pgm.width + i];
                img.setRGB(i, j, new Color(pix,pix,pix).getRGB());
            }
        }
        return img;
    }

    private MenuBar mb;

    private Menu mFile;
    private MenuItem miOpen;
    private MenuItem miSave;
    private MenuItem miSaveAs;
    private MenuItem miClose;

    private Menu mEdit;
    private MenuItem miHisto;
    private MenuItem miEtirement;
    private MenuItem miEgalisation;


    private JLabel label;


    public MyInterfaceGraphique() {
        super("Ida et Chackal");


        mb = new MenuBar();

        mFile = new Menu("File");

        miOpen = new MenuItem("Open");
        //Add actions ?
        ActionListener alOpen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Open file ppm or pgm
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM & PGM Images","ppm","pgm");
                fc.setFileFilter(filter);
                int returnval = fc.showOpenDialog(getParent());
                if (returnval == JFileChooser.APPROVE_OPTION) {
                    if (fc.getSelectedFile().toString().contains("pgm")) {
                        try {
                            ppm = null;
                            pgm = new ShortPixmap(fc.getSelectedFile().getAbsolutePath());

                            label = new JLabel(new ImageIcon(bufferImage(pgm)));

                            add(label);
                            setPreferredSize(new Dimension(pgm.width+50, pgm.height+80));
                            pack();

                            mEdit.setEnabled(true);
                            miSave.setEnabled(true);
                            miSaveAs.setEnabled(true);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (fc.getSelectedFile().toString().contains("ppm")) {
                        try {
                            pgm = null;
                            ppm = new ByteRGBPixmap(fc.getSelectedFile().getAbsolutePath());

                            mEdit.setEnabled(true);
                            miSave.setEnabled(true);
                            miSaveAs.setEnabled(true);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    System.out.println(fc.getSelectedFile().getAbsolutePath());
                    System.out.println(fc.getSelectedFile().getName());

                }
            }
        };
        miOpen.addActionListener(alOpen);
        mFile.add(miOpen);

        miSave = new MenuItem("Save");
        mFile.add(miSave);

        miSaveAs = new MenuItem("Save as");
        mFile.add(miSaveAs);

        miClose = new MenuItem("Close");
        //Add Actions ?
        ActionListener alClose = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDisplayable()) {
                    // Other stuff if needed like do you wanna save ? etc
                    dispose();
                }
            }
        };
        miClose.addActionListener(alClose);
        mFile.add(miClose);


        mEdit = new Menu("Edit");
        miHisto = new MenuItem("Histogramme");
        miEtirement = new MenuItem("Étirement");
        miEgalisation = new MenuItem("Égalisation");

        mEdit.add(miHisto);
        mEdit.add(miEtirement);
        mEdit.add(miEgalisation);

        mb.add(mFile);
        mb.add(mEdit);
        setMenuBar(mb);


        // Seulement quand aucun fichier est ouvert
        mEdit.setEnabled(false);

        miSave.setEnabled(false);
        miSaveAs.setEnabled(false);

        setPreferredSize(new Dimension(300,150));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
