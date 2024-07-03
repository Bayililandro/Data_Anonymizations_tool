package interface_fenetre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import code_anonymisation_datafly.DataFlyProcessor;
import code_anonymisation_datafly.DialogUtils;
import code_anonymisation_datafly.KanonymityProcess;
import code_anonymisation_personnalisized.CodeDataflyGen;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import create_project.Importer_Exporter;

@SuppressWarnings("serial")
public class CadreRectangulaire extends JPanel {

	private Font font;
    public static JTable tableLeft;
    public static JTable tableRight;
    private Importer_Exporter importerExporter;
    Fenetre fenetre;
   DataFlyProcessor dataFlyProcessor;
   private boolean isPersonalizationMode = false;

   private JButton btnAnonymized;
   private JButton btnSave;
   private JButton btnImport;
   private JButton btnView;
   private JButton btnPersonalisize;
   private JButton btnAnonymizeds;
   private MouseListener headerMouseListener;

    public CadreRectangulaire() {
        super(true);
        this.setBackground(Color.WHITE);
        //this.setBackground(Color.LIGHT_GRAY);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        font= new Font(" TimesRoman ",Font.BOLD+Font.PLAIN,15);//definition de la police
        // Création du bouton pour importer le fichier Excel
        Box box1 = Box.createHorizontalBox();
         btnImport = new JButton("Import data");
        btnImport.setMnemonic('I');
        btnImport.setBackground(Color.WHITE);
        btnImport.setFont(font);
        JButton btnValue = new JButton("Choose k");
        btnValue.setFont(font);
        btnValue.setMnemonic('C');
         btnAnonymized = new JButton("Anonymized");
        btnAnonymized.setFont(font);
        btnAnonymized.setMnemonic('A');
        btnAnonymized.setBackground(Color.WHITE);
         btnSave = new JButton("Save");
        btnSave.setFont(font);
        btnSave.setMnemonic('S');
        btnSave.setBackground(Color.WHITE);
         btnView = new JButton("View data");
        btnView.setFont(font);
        btnView.setMnemonic('V');
        btnView.setBackground(Color.WHITE);
         btnPersonalisize = new JButton("Personalisized");
        btnPersonalisize.setFont(font);
        btnPersonalisize.setMnemonic('P');
        btnPersonalisize.setBackground(Color.WHITE);
         btnAnonymizeds= new JButton("Anonymizeds");
        btnAnonymizeds.setFont(font);
        btnAnonymizeds.setMnemonic('A');
        btnAnonymizeds.setBackground(Color.WHITE);
        
        btnAnonymized.setVisible(false);
    	btnSave.setVisible(false);
    	btnView.setVisible(false);
    	btnAnonymizeds.setVisible(false);
    	btnPersonalisize.setVisible(false);
        
        btnImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnAnonymized.setVisible(true);
            	btnSave.setVisible(true);
            	btnView.setVisible(true);
            	btnAnonymizeds.setVisible(true);
            	btnPersonalisize.setVisible(true);
                if (tableLeft != null && tableLeft.getRowCount() > 0) {
                	DialogUtils dialog= new DialogUtils();
                    int response = dialog.showConfirmDialog();
                    if (response != JOptionPane.YES_OPTION) {
                        return; // Attendre la confirmation de l'utilisateur
                    }
                    
                    // Netoyer les tables
                    DefaultTableModel model = (DefaultTableModel) tableLeft.getModel();
                    model.setRowCount(0);
                    model.setColumnCount(0);
                    DefaultTableModel modelRight= (DefaultTableModel) tableRight.getModel();
                    modelRight.setRowCount(0);
                    modelRight.setColumnCount(0);
                }
                
                importerExporter = new Importer_Exporter(fenetre);
                List<List<String>> data = importerExporter.importExcelFile();
                if (data != null) {
                    afficherDonnees(data);
                }
            }
        });
        box1.add(btnImport);
        
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnAnonymized.setVisible(true);
            	btnAnonymizeds.setVisible(false);
            	markData();
            	btnAnonymized.setEnabled(true);
            	btnPersonalisize.setEnabled(true);
                btnSave.setEnabled(true);
            }
        });
        box1.add(btnView);
        btnAnonymizeds.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 DialogUtils.showValueSelectionDialog(fenetre);
				CodeDataflyGen.traiterColonnes();	
			}
		});
        box1.add(btnAnonymizeds);
        
        
        
        btnAnonymized.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 anonymizeData();
            }
        });
        
        box1.add(btnAnonymized);
        btnPersonalisize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				personlizedAnonymity();
				btnAnonymized.setVisible(false);
				btnAnonymizeds.setVisible(true);
			}
		});
       
        box1.add(btnPersonalisize);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechoose = new JFileChooser();
                int option = filechoose.showSaveDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String name = filechoose.getSelectedFile().getName();
                    String path = filechoose.getSelectedFile().getParentFile().getPath();
                    String file = path + "\\" + name + ".xlsx";
                    try {
                        Importer_Exporter.exportToExcel(tableRight, new File(file));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        box1.add(btnSave);
        //box1.add(save);
        add(box1);

        /*
         * 
         *  Création du JScrollPane pour la table de gauche
         * Création de la première table qui permet d'affiché les données originales
         * on désactive le fait que les colonnes soient édictable
         * 
         * */
        

        tableLeft = new JTable() {
        	
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLeft.setRowSelectionAllowed(false); // Désactive la sélection des lignes
        tableLeft.setColumnSelectionAllowed(false); // Désactive la sélection des colonnes
        // tableLeft.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         tableLeft.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Désactive le redimensionnement automatique des colonnes
        JScrollPane scrollPaneLeft = new JScrollPane(tableLeft, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Active seulement la barre de défilement horizontale
        scrollPaneLeft.setBorder(BorderFactory.createTitledBorder("Input Data to Anonymize"));
        this.add(scrollPaneLeft);

        /*
         *  Création du JScrollPane pour la table de droite 
         * Création de la duxième table
         * désactiver le fait que les colonnes de la table soient éditable par défaut
         * 
         * */
      tableRight = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableRight.setRowSelectionAllowed(false); // Désactive la sélection des lignes
        tableRight.setColumnSelectionAllowed(false); // Désactive la sélection des colonnes
        // tableRight.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableRight.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Désactive le redimensionnement automatique des colonnes
        JScrollPane scrollPaneRight = new JScrollPane(tableRight, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Active seulement la barre de défilement horizontale
        scrollPaneRight.setBorder(BorderFactory.createTitledBorder("Output Data After Anonymized"));
        this.add(scrollPaneRight);
        
        /*
         * Gerer les action du curseur
         * */
        
        btnImport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnImport.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnImport.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        btnView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnView.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnView.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        btnAnonymized.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnAnonymized.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnAnonymized.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        btnAnonymizeds.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnAnonymizeds.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnAnonymizeds.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        btnPersonalisize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnPersonalisize.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnPersonalisize.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
       
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnSave.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnSave.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
     // Créer un MouseListener pour les en-têtes de colonnes
        headerMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPersonalizationMode) {
                    int col = tableRight.columnAtPoint(e.getPoint());
                    String columnName = tableRight.getColumnName(col);

                    // Afficher une boîte de dialogue pour choisir le type de colonne
                    String[] options = {"Identifiant", "Quasi-Identifiant", "Sensible"};
                    int choice = JOptionPane.showOptionDialog(null,
                            "Choisissez le type de colonne pour " + columnName,
                            "Personnalisation de colonne",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    switch (choice) {
                        case 0:
                            CodeDataflyGen.highlightIdentifier(columnName);
                            //traiterIdentifiant(columnName);
                            break;
                        case 1:
                        	CodeDataflyGen.highlightQuasiIdentifier(columnName);
                            break;
                        case 2:
                        	CodeDataflyGen.highlightSensitive(columnName);
                            break;
                        default:
                            // Pas d'action nécessaire
                            break;
                    }
                }
            }
        };
        tableRight.getTableHeader().addMouseListener(headerMouseListener);
    }
    JScrollPane scrollPane = new JScrollPane(tableRight);

    
    
    /*
     * Methode qui definit l'action de l'anonymat des données
     * */
	@SuppressWarnings("unused")
	public void anonymizeData() {
    	// Récupérer les données de la table
        List<List<String>> data = recupererDonneesDeTable();

        if (data != null) {
            dataFlyProcessor = new DataFlyProcessor();
            
            // Récupérer les en-têtes de colonnes de la JTable tableLeft
            List<String> entetes = new ArrayList<>();
            for (int i = 0; i < tableLeft.getColumnCount(); i++) {
                entetes.add(tableLeft.getColumnName(i));
            }
            
            
             KanonymityProcess anonym= new KanonymityProcess();
             
            // Supprimer les valeurs sous les en-têtes identifiants
             int k= DialogUtils.showValueSelectionDialog(fenetre);
            dataFlyProcessor.suppressIdentifyingAttributes(data, entetes);
            //int k= DialogUtils.showValueSelectionDialog(fenetre);
            //anonym.applyKAnonymity(data, k);
           
            // Afficher les données traitées dans la JTable tableRight
             afficherDonneesTraitees(data, entetes);
            
        }
    }
    
    /*
     * Methode pour maquer de l'action qui marque les données
     * */
    public void markData() {
    	 // Récupérer les données de la table
        List<List<String>> data = recupererDonneesDeTable();

        if (data != null) {
            dataFlyProcessor = new DataFlyProcessor();
            
            // Récupérer les en-têtes de colonnes de la JTable tableLeft
            List<String> entetes = new ArrayList<>();
            for (int i = 0; i < tableLeft.getColumnCount(); i++) {
                entetes.add(tableLeft.getColumnName(i));
            }
                                
            // Supprimer les valeurs sous les en-têtes identifiants
            dataFlyProcessor.markIdentifyingAttributes(entetes);
            dataFlyProcessor.markQuasiIdentifyingAttribut(entetes);
            dataFlyProcessor.markSensitiveIdentifyingAttribut(entetes);
            afficherDonneesTraitees(data, entetes);
            
        }
            
    }
    
    private void personlizedAnonymity() {
    	// Afficher une boîte de dialogue pour demander la confirmation
        int confirm = JOptionPane.showConfirmDialog(null,
                "Souhaitez-vous personnaliser la détection du type d'attribut?",
                "Confirmation de personnalisation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Récupération des données importées dans tableLeft
        	btnAnonymized.setVisible(false);
            List<List<String>> data = recupererDonneesDeTable();
            if (data != null) {
                List<String> headers = new ArrayList<>();
                for (int i = 0; i < tableLeft.getColumnCount(); i++) {
                    headers.add(tableLeft.getColumnName(i));
                }
                afficherDonneesTraitees(data, headers);
                isPersonalizationMode = true;
            }
        }
    }

    /*
     * 
     *  Méthode pour gérer l'affichage des données
     * Les données sont tabulaire alors on utilise une liste de liste
     * pour chaque fiché chargé on ajoute une colonne supplémentaire pour la numerotation des valeurs
     * ici la methode permet l'affichage des données dans la tableRight
     * 
     * */
    
    public void afficherDonnees(List<List<String>> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("N°"); // Ajoute une colonne pour la numérotation des lignes
        for (String header : data.get(0)) {
            model.addColumn(header);
        }
        for (int i = 1; i < data.size(); i++) {
            List<String> rowData = data.get(i);
            Object[] rowWithIndex = new Object[rowData.size() + 1];
            rowWithIndex[0] = i; // Numérotation de la ligne
            for (int j = 0; j < rowData.size(); j++) {
                rowWithIndex[j + 1] = rowData.get(j);
            }
            model.addRow(rowWithIndex);
        }

        if (tableLeft == null) {
        	tableLeft = new JTable(model);
            add(new JScrollPane(tableLeft));
        } else {
        	tableLeft.setModel(model);
        }

        revalidate();
        repaint();
    }
   
   public List<List<String>> recupererDonneesDeTable() {
	    List<List<String>> donnees = new ArrayList<>(); // Initialisation de la liste de données

	    DefaultTableModel model = (DefaultTableModel) tableLeft.getModel(); // Récupération du modèle de la table

	    int rowCount = model.getRowCount(); // Nombre de lignes dans la table

	    // Parcours de chaque ligne de la table
	    for (int i = 0; i < rowCount; i++) {
	        List<String> ligne = new ArrayList<>(); // Initialisation de la liste pour stocker les valeurs de la ligne

	        int columnCount = model.getColumnCount(); // Nombre de colonnes dans la table

	        // Parcours de chaque colonne de la ligne
	        for (int j = 0; j < columnCount; j++) { // Commencer à partir de 1 pour ignorer la colonne de numéro de ligne
	            // Récupération de la valeur de la cellule à la position (i, j)
	            Object cellValue = model.getValueAt(i, j);

	            // Ajout de la valeur à la liste de valeurs de la ligne
	            ligne.add(cellValue != null ? cellValue.toString() : "");
	        }

	        // Ajout de la liste de valeurs de la ligne à la liste de données
	        donnees.add(ligne);
	    }

	    return donnees; // Retourne la liste de données récupérées de la table
	}

   public void afficherDonneesTraitees(List<List<String>> donneesTraitees, List<String> entetes) {
	    // Affichage des données traitées dans la table
	    DefaultTableModel model = new DefaultTableModel();

	    // Ajout des en-têtes de colonnes à partir de la liste d'en-têtes fournie
	    for (String entete : entetes) {
	        model.addColumn(entete);
	    }

	    // Ajout des données traitées dans le modèle de la table
	    for (List<String> rowData : donneesTraitees) {
	        model.addRow(rowData.toArray());
	    }

	    // Remplacer le modèle de la table avec le nouveau modèle contenant les données traitées
	    tableRight.setModel(model);
	}
       
}
