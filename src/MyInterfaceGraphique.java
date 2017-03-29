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
    private JMenuItem miReset;
    private JMenuItem miExit;

    private JMenu mEdit;
    private JMenu mHisto;
    private JMenu mFiltre;
    private JMenu mLogic;
    private JMenuItem miHisto;
    private JMenuItem miEtirement;
    private JMenuItem miEgalisation;
    private JMenuItem miSpecification;
    private JMenuItem miNagao;
    private JMenuItem miMedian;
    private JMenuItem miOtsu;
    private JMenuItem miAnd;
    private JMenuItem miOr;
    
    private JLabel label;

    public MyInterfaceGraphique() {
        super("Traitement d'images");

        label = new JLabel();

        mb = new JMenuBar();

        //Menu contenant les actions image
        mFile = new JMenu("File");

        //Permet d'ouvrir une nouvelle image
        miOpen = new JMenuItem("Open");
        ActionListener alOpen = e -> {
            //Ouvrir une image ppm ou pgm
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
                //Cas d'une image pgm
                if (fc.getSelectedFile().toString().endsWith(".pgm")) {
                    try {
                        ppm = null;
                        //Récupération de l'image
                        pgm = new ShortPixmap(fc.getSelectedFile().getAbsolutePath());
                        label.setIcon(new ImageIcon(bufferImagePGM(pgm)));

                        setPreferredSize(new Dimension(pgm.width + 50, pgm.height + 80));
                        pack();

                        //On rend les boutons utilisables
                        mEdit.setEnabled(true);
                        miSave.setEnabled(true);
                        miSaveAs.setEnabled(true);
                        miReset.setEnabled(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                //Cas d'une image ppm
                if (fc.getSelectedFile().toString().endsWith(".ppm")) {
                    try {
                        pgm = null;
                        //Récupération de l'image
                        ppm = new ByteRGBPixmap(fc.getSelectedFile().getAbsolutePath());
                        label.setIcon(new ImageIcon(bufferImagePPM(ppm)));

                        setPreferredSize(new Dimension(ppm.width + 50, ppm.height + 80));
                        pack();

                        //On rend les boutons utilisables
                        mEdit.setEnabled(true);
                        miSave.setEnabled(true);
                        miSaveAs.setEnabled(true);
                        miReset.setEnabled(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                path = fc.getSelectedFile().getAbsolutePath();
            }
        };
        miOpen.addActionListener(alOpen);
        mFile.add(miOpen);

        //Permet de sauvegarder et écraser l'image
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

        //Permet de sauvegarder l'image à l'endroit de son choix
        miSaveAs = new JMenuItem("Save as");
        ActionListener alSaveAs = e -> {
            //Cas d'une image pgm
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
            //Cas d'une image ppm
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

        //Permet de réinitialiser l'image et supprimer toutes modifications
        miReset = new JMenuItem("Reset");
        ActionListener alReset = e -> {
            //Cas d'une image pgm
            if (pgm != null) {
                try {
                    pgm = new ShortPixmap(path);
                    label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
                } catch (IOException e1) {
                        e1.printStackTrace();
                }
            }
            //Cas d'une image ppm
            else if (ppm != null) {
                try {
                    ppm = new ByteRGBPixmap(path);
                    label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
                } catch (IOException e1) {
                        e1.printStackTrace();
                }
            }
        };
        miReset.addActionListener(alReset);
        mFile.add(miReset);

        //Permet de fermer l'application
        miExit = new JMenuItem("Exit");
        ActionListener alExit = e -> {
            if (isDisplayable()) {
                dispose();
            }
        };
        miExit.addActionListener(alExit);
        mFile.add(miExit);

        //Menu les actions d'édition de l'image
        mEdit = new JMenu("Edit");

        //Concerne les opérations sur l'histogramme de l'image
        mHisto = new JMenu("Histogramme");

        miHisto = new JMenuItem("Afficher");
        miEtirement = new JMenuItem("Étirement");
        miEgalisation = new JMenuItem("Égalisation");
        miSpecification = new JMenuItem("Spécification");

        //Permet d'afficher l'histogramme de l'image
        ActionListener alHisto = e -> {
            //Cas d'une image pgm
            if (pgm != null) {
                ShortPixmap pgmHisto = new ShortPixmap(256, 256, MyClassPGM.histogramme(pgm.data));
                JFrame tmp = new JFrame("Histogramme");
                JLabel labelHisto = new JLabel();
                labelHisto.setIcon(new ImageIcon(bufferImagePGM(pgmHisto)));
                labelHisto.setHorizontalAlignment(JLabel.CENTER);
                tmp.add(labelHisto);
                tmp.getContentPane().setBackground(Color.BLACK);
                tmp.setPreferredSize(new Dimension(pgmHisto.width + 50, pgmHisto.height + 50));
                tmp.pack();
                tmp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tmp.setVisible(true);
            //Cas d'une image ppm
            } else if (ppm != null) {
                ByteRGBPixmap pgmHisto = new ByteRGBPixmap(256, 256, Pixmap.getBytes(MyClassPGM.histogramme(ppm.r.getShorts())), Pixmap.getBytes(MyClassPGM.histogramme(ppm.g.getShorts())), Pixmap.getBytes(MyClassPGM.histogramme(ppm.b.getShorts())));
                JFrame tmp = new JFrame("Histogramme");
                JLabel labelHisto = new JLabel();
                labelHisto.setIcon(new ImageIcon(bufferImagePPM(pgmHisto)));
                labelHisto.setHorizontalAlignment(JLabel.CENTER);
                tmp.add(labelHisto);
                tmp.getContentPane().setBackground(Color.BLACK);
                tmp.setPreferredSize(new Dimension(pgmHisto.width + 50, pgmHisto.height + 50));
                tmp.pack();
                tmp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                tmp.setVisible(true);
            }
        };
        miHisto.addActionListener(alHisto);

        //Permet d'étirer l'histogramme de l'image
        ActionListener alEtirement = e -> {
            //Cas d'une image pgm
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.etirementHisto(pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            //Cas d'une image ppm
            } else if (ppm != null) {
                ppm = MyClassPPM.etirementHisto(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miEtirement.addActionListener(alEtirement);

        //Permet d'égaliser l'histogramme de l'image
        ActionListener alEgalisation = e -> {
            //Cas d'une image pgm
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.egalisationHisto(pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            //Cas d'une image ppm
            } else if (ppm != null) {
                ppm = MyClassPPM.egalisationHisto(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miEgalisation.addActionListener(alEgalisation);

        //Permet de spécifier l'histogramme de l'image actuelle par rapport à l'histogramme d'une autre image
        ActionListener alSpecification = e -> {
            //Cas d'une image pgm
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
            //Cas d'une imge ppm
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

        //Concerne les opération de filtre sur l'image
        mFiltre = new JMenu("Filtres");

        //Applique le filtre médian à l'image
        miMedian = new JMenuItem("Médian");
        ActionListener alMedian = e -> {
            //Cas d'une image pgm
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.filtreMedian(pgm.width, pgm.height, pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            //Cas d'une image ppm
            } else if (ppm != null) {
                ppm = MyClassPPM.filtreMedian(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miMedian.addActionListener(alMedian);

        //Applique le filtre Nagao à l'image
        miNagao = new JMenuItem("Nagao");
        ActionListener alNagao = (ActionEvent ae) -> {
            //Cas d'une image pgm
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.filtreNagao(pgm.width, pgm.height, pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            //Cas d'une image ppm
            } else if (ppm != null) {
                ppm = MyClassPPM.filtreNagao(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miNagao.addActionListener(alNagao);

        //Applique le filtre Otsu à l'image
        miOtsu = new JMenuItem("Otsu");
        ActionListener alOtsu = (ActionEvent ae) -> {
            //Cas d'une image pgm
            if (pgm != null) {
                pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.binarisation(pgm.data));
                label.setIcon(new ImageIcon(bufferImagePGM(pgm)));
            //Cas d'une image ppm
            } else if (ppm != null) {
                ppm = MyClassPPM.binarisation(ppm);
                label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
            }
        };
        miOtsu.addActionListener(alOtsu);
        
        mLogic = new JMenu("Logic");
        miAnd = new JMenuItem("And");
        ActionListener alAnd = e -> {
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
                        pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.and(pgm.data, tmp.data));
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
                        ppm = MyClassPPM.and(ppm, tmp);
                        label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
                    } else {
                        JOptionPane.showMessageDialog(null, "Mauvais format de fichier", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };
        miAnd.addActionListener(alAnd);
        miOr = new JMenuItem("Or");
        ActionListener alOr = e -> {
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
                        pgm = new ShortPixmap(pgm.width, pgm.height, MyClassPGM.or(pgm.data, tmp.data));
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
                        ppm = MyClassPPM.or(ppm, tmp);
                        label.setIcon(new ImageIcon(bufferImagePPM(ppm)));
                    } else {
                        JOptionPane.showMessageDialog(null, "Mauvais format de fichier", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };
        miOr.addActionListener(alOr);

        //Ajouts de chaque sous-menu au menu
        mHisto.add(miHisto);
        mHisto.add(miEtirement);
        mHisto.add(miEgalisation);
        mHisto.add(miSpecification);

        //Ajouts de chaque bouton aux sous-menus
        mFiltre.add(miMedian);
        mFiltre.add(miNagao);

        mLogic.add(miAnd);
        mLogic.add(miOr);
        
        mEdit.add(mHisto);
        mEdit.add(mFiltre);
        mEdit.add(miOtsu);
        mEdit.add(mLogic);

        mb.add(mFile);
        mb.add(mEdit);
        setJMenuBar(mb);

        //Affichage de l'image
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        //Rend inutilisables certains boutons tant qu'aucune image n'est chargée
        mEdit.setEnabled(false);

        miSave.setEnabled(false);
        miSaveAs.setEnabled(false);
        miReset.setEnabled(false);

        //Affichage de la fenêtre
        setPreferredSize(new Dimension(300, 150));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}
