package interface_fenetre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.Graphics;
import java.awt.event.*;

import code_anonymisation_datafly.DataFlyProcessor;
import code_anonymisation_datafly.DialogUtils;
import code_anonymisation_datafly.KanonymityProcess;
import code_anonymisation_personnalisized.CodeDataflyGen;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import create_project.Importer_Exporter;

@SuppressWarnings("serial")
public class CadreRectangulaire extends JPanel {

	private Font font;
    private JTable tableLeft;
    private JTable tableRight;
    private Importer_Exporter importerExporter;
    Fenetre fenetre;
   DataFlyProcessor dataFlyProcessor;
   private boolean isPersonalizationMode = false;
   
   private Set<String> identifierColumns = new HashSet<>();
   private Set<String> quasiIdentifierColumns = new HashSet<>();
   private Set<String> sensitiveColumns = new HashSet<>();

   private MouseListener headerMouseListener;

    public CadreRectangulaire() {
        super(true);
        this.setBackground(Color.WHITE);
        //this.setBackground(Color.LIGHT_GRAY);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        font= new Font(" TimesRoman ",Font.BOLD+Font.PLAIN,15);//definition de la police
        // Création du bouton pour importer le fichier Excel
        Box box1 = Box.createHorizontalBox();
        JButton importButton = new JButton("Import data");
        importButton.setMnemonic('I');
        importButton.setBackground(Color.WHITE);
        importButton.setFont(font);
        JButton value = new JButton("Choose k");
        value.setFont(font);
        value.setMnemonic('C');
        JButton anonymized = new JButton("Anonymized");
        anonymized.setFont(font);
        anonymized.setMnemonic('A');
        anonymized.setBackground(Color.WHITE);
        JButton save = new JButton("Save");
        save.setFont(font);
        save.setMnemonic('S');
        save.setBackground(Color.WHITE);
        JButton view = new JButton("View data");
        view.setFont(font);
        view.setMnemonic('V');
        view.setBackground(Color.WHITE);
        JButton personalisize = new JButton("Personalisized");
        personalisize.setFont(font);
        personalisize.setMnemonic('P');
        personalisize.setBackground(Color.WHITE);
        
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableLeft != null && tableLeft.getRowCount() > 0) {
                    int response = showConfirmDialog();
                    if (response != JOptionPane.YES_OPTION) {
                        return; // Do nothing if the user does not confirm
                    }
                    
                    // Clear the table
                    DefaultTableModel model = (DefaultTableModel) tableLeft.getModel();
                    model.setRowCount(0);
                    model.setColumnCount(0);
                    DefaultTableModel modelRight= (DefaultTableModel) tableRight.getModel();
                    modelRight.setRowCount(0);
                    modelRight.setColumnCount(0);
                }
                
                view.setEnabled(false);
                anonymized.setEnabled(false);
                personalisize.setEnabled(false);
                save.setEnabled(false);
                
                importerExporter = new Importer_Exporter(fenetre);
                List<List<String>> data = importerExporter.importExcelFile();
                if (data != null) {
                    afficherDonnees(data);
                    view.setEnabled(true);
                }
            }
        });
        box1.add(importButton);
        
        
        value.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 int selectedValue = DialogUtils.showValueSelectionDialog(fenetre);
	                System.out.println("Selected value: " + selectedValue);
			}
		});
        //box1.add(value);
        
        
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	markData();
            	anonymized.setEnabled(true);
                personalisize.setEnabled(true);
                save.setEnabled(true);
            }
        });
        box1.add(view);
        
        
        
        anonymized.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 anonymizeData();
            }
        });
        
        box1.add(anonymized);
        personalisize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				personlizedAnonymity();
			}
		});
       
        box1.add(personalisize);
        save.addActionListener(new ActionListener() {
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
        box1.add(save);
        box1.add(save);
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
        
        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                importButton.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
                importButton.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                view.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	view.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        anonymized.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                anonymized.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	anonymized.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        personalisize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	personalisize.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	personalisize.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
       
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                save.setBackground(Color.GREEN); // Changer la couleur au survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
                save.setBackground(Color.WHITE); // Revenir à la couleur d'origine
            }
        });
        
        
        // Ajouter un écouteur d'événements aux en-têtes de colonnes de la JTable
      /*  JTableHeader header = tableRight.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnIndex = header.columnAtPoint(e.getPoint());
                String columnName = tableRight.getColumnName(columnIndex);
                // Ouvrir une boîte de dialogue pour choisir le type d'attribut
                showAttributeTypeSelectionDialog(columnName);
            }
        });       
    }*/
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
                            highlightIdentifier(columnName);
                            break;
                        case 1:
                            highlightQuasiIdentifier(columnName);
                            break;
                        case 2:
                            highlightSensitive(columnName);
                            break;
                        default:
                            // Pas d'action nécessaire
                            break;
                    }
                }
            }
        };
    }
    JScrollPane scrollPane = new JScrollPane(tableRight);

    /*
     * Méthode qui permet l'ouverture d'une boite de dialogue pour la sélection
     * des catégorie de données par l'utilisateur
     * à chaque catégorie on ajout des étiquêtes spécifique pour les distinguer
     * */
    /*private void showAttributeTypeSelectionDialog(String columnName) {
        // Créer une boîte de dialogue pour choisir le type d'attribut
        String[] options = {"Sensible", "Identifiant", "Quasi-Identifiant", "Aucun"};
        int choice = JOptionPane.showOptionDialog(null, "Choose attribute type '" + columnName + "':", 
                                                   "Attribute Type Selection", JOptionPane.DEFAULT_OPTION, 
                                                   JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        // Appeler la méthode correspondante pour marquer l'attribut après la sélection du type d'attribut
        if (choice != JOptionPane.CLOSED_OPTION) {
            switch (options[choice]) {
                case "Identifiant":
                    highlightIdentifier(columnName);
                    break;
                case "Sensible":
                    highlightSensitive(columnName);
                    break;
                case "Quasi-Identifiant":
                    highlightQuasiIdentifier(columnName);
                    break;
            }
        }
    }*/
    
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
            //dataFlyProcessor.generalizeQuasiIdentifyingAttribute(data, entetes);
            // Afficher les données traitées dans la JTable tableRight
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
            List<List<String>> data = recupererDonneesDeTable();
            if (data != null) {
                List<String> headers = new ArrayList<>();
                for (int i = 0; i < tableLeft.getColumnCount(); i++) {
                    headers.add(tableLeft.getColumnName(i));
                }
                afficherDonneesTraitees(data, headers);
                isPersonalizationMode = true;
                //JOptionPane.showMessageDialog(null, "Mode de personnalisation activé. Cliquez sur les en-têtes de colonne pour les personnaliser.");
                tableRight.getTableHeader().addMouseListener(headerMouseListener);
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

    // boite de dialogue qui permet un message de confirmation lorque les tables ne sont pas vide
    private int showConfirmDialog() {
        // Create a red question mark icon
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
        g.setColor(Color.RED);
        g.fillOval(0, 0, 32, 32);
        g.setColor(Color.WHITE);
        g.drawString("?", 12, 22);
        g.dispose();
        Icon icon = new ImageIcon(image);

        // Show the custom confirm dialog with the red question mark icon
        return JOptionPane.showConfirmDialog(this,
                "Table is not empty, are you sure to overwrite it",
                "Confirm Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
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
   
   
   private void highlightIdentifier(String columnName) {
       highlightColumn(columnName, Color.RED);
       identifierColumns.add(columnName);
       quasiIdentifierColumns.remove(columnName);
       sensitiveColumns.remove(columnName);
   }

   private void highlightSensitive(String columnName) {
       highlightColumn(columnName, Color.GREEN);
       sensitiveColumns.add(columnName);
       identifierColumns.remove(columnName);
       quasiIdentifierColumns.remove(columnName);
   }

   private void highlightQuasiIdentifier(String columnName) {
       highlightColumn(columnName, Color.YELLOW);
       quasiIdentifierColumns.add(columnName);
       identifierColumns.remove(columnName);
       sensitiveColumns.remove(columnName);
   }

	// methode permettant de marquer les en-têtes des colonnes lors du click pour la personnalisation
   private void highlightColumn(String columnName, Color color) {
       TableColumnModel columnModel = tableRight.getColumnModel();
       int columnIndex = getColumnIndexByName(columnName);
       TableColumn column = columnModel.getColumn(columnIndex);
       String dotColor;
       if (color.equals(Color.RED)) {
           dotColor = "red";
       } else if (color.equals(Color.GREEN)) {
           dotColor = "green";
       } else if (color.equals(Color.YELLOW)) {
           dotColor = "yellow";
       } else {
           dotColor = "black"; // Default color
       }
       column.setHeaderValue("<html><font color='blue'>" + columnName + "</font> <font color='" + dotColor + "'>●</font></html>");
       tableRight.getTableHeader().repaint(); // Rafraîchir l'en-tête
   }
   private int getColumnIndexByName(String columnName) {
       for (int i = 0; i < tableRight.getColumnCount(); i++) {
           if (tableRight.getColumnName(i).equals(columnName)) {
               return i;
           }
       }
       return -1;
   }
   
}
