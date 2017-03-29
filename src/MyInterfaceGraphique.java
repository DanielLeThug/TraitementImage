
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Simon on 21/03/2017.
 */
public class MyInterfaceGraphique extends JFrame {

    private ShortPixmap pgm = null;
    private ByteRGBPixmap ppm = null;
    private String path = null;

    private BufferedImage bufferImagePGM(ShortPixmap pgm) {
        BufferedImage img = new BufferedImage(pgm.width, pgm.height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < pgm.width; i++) {
            for (int j = 0; j < pgm.height; j++) {
                short pix = pgm.data[j * pgm.width + i];
                img.setRGB(i, j, new Color(pix, pix, pix).getRGB());
            }
        }
        return img;
    }

    private BufferedImage bufferImagePPM(ByteRGBPixmap ppm) {
        BufferedImage img = new BufferedImage(ppm.width, ppm.height, BufferedImage.TYPE_INT_RGB);
        short[] red = ppm.r.getShorts();
        short[] green = ppm.g.getShorts();
        short[] blue = ppm.b.getShorts();
        for (int i = 0; i < ppm.width; i++) {
            for (int j = 0; j < ppm.height; j++) {
                short redpix = red[j * ppm.width + i];
                short greenpix = green[j * ppm.width + i];
                short bluepix = blue[j * ppm.width + i];
                img.setRGB(i, j, new Color(redpix, greenpix, bluepix).getRGB());
            }
        }
        return img;
    }

    private JMenuBar mb;

    private JMenu mFile;
    private JMenuItem miOpen;
    private JMenuItem miSave;
    private JMenuItem miSaveAs;
    private JMenuItem miClose;

    private JMenu mEdit;
    private JMenu mHisto;
    private JMenu mFiltre;
    private JMenuItem miHisto;
    private JMenuItem miEtirement;
    private JMenuItem miEgalisation;
    private JMenuItem miSpecification;
    private JMenuItem miNagao;
    private JMenuItem miMedian;
    private JMenuItem miOtsu;

    private JLabel label;

    public MyInterfaceGraphique() {
        super("Ida et Chackal");

        label = new JLabel();

        mb = new JMenuBar();

        mFile = new JMenu("File");

        miOpen = new JMenuItem("Open");
        //Add actions ?
        ActionListener alOpen = e -> {
            //Open file ppm or pgm
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileNameExtensionFilter("PGM Images", "pgm"));
            fc.addChoosableFileFilter(new FileNameExtensionFilter("PPM Images", "ppm"));
            fc.setFileFilter(new FileNameExtensionFilter("PPM & PGM Images", "ppm", "pgm"));
            fc.setDialogTitle("Open");
            fc.setCurrentDirectory(new File("./"));
            fc.setApproveButtonText("Open");
            fc.setDragEnabled(false);
            int returnval = fc.showOpenDialog(getParent());
            if (returnval == JFileChooser.APPROVE_OPTION) {
                if (fc.getSelectedFile().toString().endsWith(".pgm")) {
                    try {
                        ppm = null;
                        pgm = new ShortPixmap(fc.getSelectedFile().getAbsolutePath());
                        label.setIcon(new ImageIcon(bufferImagePGM(pgm)));

                        setPreferredSize(new Dimension(pgm.width + 50, pgm.height + 80));
                        pack();

                        mEdit.setEnabled(true);
                        miSave.setEnabled(true);
                        miSaveAs.setEnabled(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                if (fc.getSelectedFile().toString().endsWith(".ppm")) {
                    try {
                        pgm = null;
                        ppm = new ByteRGBPixmap(fc.getSelectedFile().getAbsolutePath());
                        label.setIcon(new ImageIcon(bufferImagePPM(ppm)));

                        setPreferredSize(new Dimension(ppm.width + 50, ppm.height + 80));
                        pack();

                        mEdit.setEnabled(true);
                        miSave.setEnabled(true);
                        miSaveAs.setEnabled(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                path = fc.getSelectedFile().getAbsolutePath();
            }
        };
        miOpen.addActionListener(alOpen);
        mFile.add(miOpen);

        miSave = new JMenuItem("Save");
        ActionListener alSave = e -> {
            if (pgm != null) {
                pgm.write(path);
                JOptionPane.showMessageDialog(null, "File saved", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else if (ppm != null) {
                ppm.write(path);
                JOptionPane.showMessageDialog(null, "File saved", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        miSave.addActionListener(alSave);
        mFile.add(miSave);

        miSaveAs = new JMenuItem("Save as");
        ActionListener alSaveAs = e -> {
            if (pgm != null) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("PGM Images", "pgm"));
                fc.setDialogTitle("Save as");
                fc.setCurrentDirectory(new File("./"));
                fc.setDragEnabled(false);
                int returnval = fc.showDialog(getParent(), "Save");
                if (returnval == JFileChooser.APPROVE_OPTION) {
                    if (fc.getSelectedFile().toString().endsWith(".pgm")) {
                        pgm.write(fc.getSelectedFile().toString());
                        JOptionPane.showMessageDialog(null, "File saved", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        pgm.write(fc.getSelectedFile().toString() + ".pgm");
                        JOptionPane.showMessageDialog(null, "File saved", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else if (ppm != null) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("PPM Images", "ppm"));
                fc.setDialogTitle("Save as");
                fc.setCurrentDirectory(new File("./"));
                fc.setDragEnabled(false);
                int returnval = fc.showDialog(getParent(), "Save");
                if (returnval == JFileChooser.APPROVE_OPTION) {
                    if (fc.getSelectedFile().toString().endsWith(".ppm")) {
                        ppm.write(fc.getSelectedFile().toString());
                        JOptionPane.showMessageDialog(null, "File saved", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        ppm.write(fc.getSelectedFile().toString() + ".ppm");
                        JOptionPane.showMessageDialog(null, "File saved", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        };
        miSaveAs.addActionListener(alSaveAs);
        mFile.add(miSaveAs);

        miClose = new JMenuItem("Close");
        //Add Actions ?
        ActionListener alClose = e -> {
            if (isDisplayable()) {
                // Other stuff if needed like do you wanna save ? etc
                dispose();
            }
        };
        miClose.addActionListener(alClose);
        mFile.add(miClose);

        mEdit = new JMenu("Edit");

        mHisto = new JMenu("Histo"); // A MODIFIER
        miHisto = new JMenuItem("Histogramme");
        miEtirement = new JMenuItem("Étirement");
        miEgalisation = new JMenuItem("Égalisation");
        miSpecification = new JMenuItem("Spécification");

        // ACTIONLISTENER TO DO
        ActionListener alHisto = e -> {
            if (pgm != null) {
                ShortPixmap pgmHisto = new ShortPixmap(256, 256, MyClassPGM.histogramme(pgm.data));
                JFrame tmp = new JFrame("Histogramme");
                JLabel labelHisto = new JLabel();
                labelHisto.setIcon(new ImageIcon(bufferImagePGM(pgmHisto)));
                labelHisto.setHorizontalAlignment(JLabel.CENTER);
                tmp.add(labelHisto);
                tmp.setPreferredSize(new Dimension(pgmHisto.width + 50, pgmHisto.height + 50));
                tmp.pack();
                tmp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tmp.setVisible(true);
            } else if (ppm != null) {
                ///////////////// ROUGE
                ShortPixmap pgmHistoR = new ShortPixmap(256, 256, MyClassPGM.histogramme(ppm.r.getShorts()));
                JFrame tmpR = new JFrame("Histogramme du rouge");
                JLabel labelHistoR = new JLabel();
                labelHistoR.setIcon(new ImageIcon(bufferImagePGM(pgmHistoR)));
                labelHistoR.setHorizontalAlignment(JLabel.CENTER);
                tmpR.add(labelHistoR);
                tmpR.setPreferredSize(new Dimension(pgmHistoR.width + 50, pgmHistoR.height + 50));
                tmpR.pack();
                tmpR.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tmpR.setVisible(true);
                ///////////////////// VERT
                ShortPixmap pgmHistoV = new ShortPixmap(256, 256, MyClassPGM.histogramme(ppm.g.getShorts()));
                JFrame tmpV = new JFrame("Histogramme du vert");
                JLabel labelHistoV = new JLabel();
                labelHistoV.setIcon(new ImageIcon(bufferImagePGM(pgmHistoV)));
                labelHistoV.setHorizontalAlignment(JLabel.CENTER);
                tmpV.add(labelHistoV);
                tmpV.setPreferredSize(new Dimension(pgmHistoV.width + 50, pgmHistoV.height + 50));
                tmpV.pack();
                tmpV.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tmpV.setVisible(true);
                ///////////////////// BLEU
                ShortPixmap pgmHistoB = new ShortPixmap(256, 256, MyClassPGM.histogramme(ppm.b.getShorts()));
                JFrame tmpB = new JFrame("Histogramme du bleu");
                JLabel labelHistoB = new JLabel();
                labelHistoB.setIcon(new ImageIcon(bufferImagePGM(pgmHistoB)));
                labelHistoB.setHorizontalAlignment(JLabel.CENTER);
                tmpB.add(labelHistoB);
                tmpB.setPreferredSize(new Dimension(pgmHistoB.width + 50, pgmHistoB.height + 50));
                tmpB.pack();
                tmpB.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tmpB.setVisible(true);
            }
        };
        miHisto.addActionListener(alHisto);

        ActionListener alEtirement = e -> {
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.etirementHisto(pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            } else if (ppm != null) {
                ppm = MyClassPPM.etirementHisto(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miEtirement.addActionListener(alEtirement);

        ActionListener alEgalisation = e -> {
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.egalisationHisto(pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            } else if (ppm != null) {
                ppm = MyClassPPM.egalisationHisto(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miEgalisation.addActionListener(alEgalisation);

        ActionListener alSpecification = e -> {
            if (pgm != null) {
                ShortPixmap tmp = null;
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM Images", "pgm");
                fc.setFileFilter(filter);
                fc.setDialogTitle("Open");
                fc.setCurrentDirectory(new File("./"));
                fc.setApproveButtonText("Open");
                fc.setDragEnabled(false);
                int returnval = fc.showOpenDialog(getParent());
                if (returnval == JFileChooser.APPROVE_OPTION) {
                    if (fc.getSelectedFile().toString().endsWith(".pgm")) {
                        try {
                            tmp = new ShortPixmap(fc.getSelectedFile().getAbsolutePath());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.specificationHisto(pgm.data, tmp.data));
                        label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
                    } else {
                        JOptionPane.showMessageDialog(null, "Mauvais format de fichier", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else if (ppm != null) {
                ByteRGBPixmap tmp = null;
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM Images", "ppm");
                fc.setFileFilter(filter);
                fc.setDialogTitle("Open");
                fc.setCurrentDirectory(new File("./"));
                fc.setApproveButtonText("Open");
                fc.setDragEnabled(false);
                int returnval = fc.showOpenDialog(getParent());
                if (returnval == JFileChooser.APPROVE_OPTION) {
                    if (fc.getSelectedFile().toString().endsWith(".ppm")) {
                        try {
                            tmp = new ByteRGBPixmap(fc.getSelectedFile().getAbsolutePath());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        ppm = MyClassPPM.specificationHisto(ppm, tmp);
                        label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
                    } else {
                        JOptionPane.showMessageDialog(null, "Mauvais format de fichier", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };
        miSpecification.addActionListener(alSpecification);

        mFiltre = new JMenu("Filtres");
        miMedian = new JMenuItem("Médian");
        ActionListener alMedian = e -> {
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.filtreMedian(pgm.width, pgm.height, pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            } else if (ppm != null) {
                ppm = MyClassPPM.filtreMedian(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miMedian.addActionListener(alMedian);

        miNagao = new JMenuItem("Nagao");
        ActionListener alNagao = (ActionEvent ae) -> {
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.filtreNagao(pgm.width, pgm.height, pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            } else if (ppm != null) {
                ppm = MyClassPPM.filtreNagao(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miNagao.addActionListener(alNagao);

        miOtsu = new JMenuItem("Otsu");
        ActionListener alOtsu = (ActionEvent ae) -> {
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.binarisation(pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            } else if (ppm != null) {
                ppm = MyClassPPM.binarisation(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miOtsu.addActionListener(alOtsu);

        // ACTIONLISTENER TO DO
        mHisto.add(miHisto);
        mHisto.add(miEtirement);
        mHisto.add(miEgalisation);
        mHisto.add(miSpecification);

        mFiltre.add(miMedian);
        mFiltre.add(miNagao);

        mEdit.add(mHisto);
        mEdit.add(mFiltre);
        mEdit.add(miOtsu);

        mb.add(mFile);
        mb.add(mEdit);
        setJMenuBar(mb);

        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        // Seulement quand aucun fichier est ouvert
        mEdit.setEnabled(false);

        miSave.setEnabled(false);
        miSaveAs.setEnabled(false);

        setPreferredSize(new Dimension(300, 150));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
