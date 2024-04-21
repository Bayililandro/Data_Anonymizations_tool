package interface_fenetre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import code_anonymisation_datafly.DataFlyProcessor;
import code_anonymisation_datafly.DialogUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import create_project.Importer_Exporter;

@SuppressWarnings("serial")
public class CadreRectangulaire extends JPanel {

    private JTable tableLeft;
    private JTable tableRight;
    private Importer_Exporter importerExporter;
    Fenetre fenetre;
   DataFlyProcessor dataFlyProcessor;

    public CadreRectangulaire() {
        super(true);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Création du bouton pour importer le fichier Excel
        Box box1 = Box.createHorizontalBox();
        JButton importButton = new JButton("Import Excel File");
        importButton.setMnemonic('I');
        JButton value = new JButton("Choose k");
        value.setMnemonic('C');
        JButton anonymized = new JButton("Anonymized");
        anonymized.setMnemonic('A');
        JButton view = new JButton("View data");
        view.setMnemonic('V');
        JButton personalisize = new JButton("Personalisized");
        personalisize.setMnemonic('P');
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importerExporter = new Importer_Exporter(fenetre);
                List<List<String>> data = importerExporter.importExcelFile();
                if (data != null) {
                    afficherDonnees(data);
                }
            }
        });
        box1.add(importButton);
        
        
        value.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 String selectedValue = DialogUtils.showValueSelectionDialog(fenetre);
	                System.out.println("Selected value: " + selectedValue);
			}
		});
        box1.add(value);
        
        
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 // Récupérer les données de la table
                List<List<String>> data = recupererDonneesDeTable();

                if (data != null) {
                    dataFlyProcessor = new DataFlyProcessor();
                    
                    // Récupérer les en-têtes de colonnes de la JTable tableLeft
                    List<String> entetes = new ArrayList<>();
                    for (int i = 0; i < tableLeft.getColumnCount(); i++) {
                        entetes.add(tableLeft.getColumnName(i));
                    }
                    
                    // Marquer les attributs quasi-identifiants
                    dataFlyProcessor.markQuasiIdentifyingAttributes(entetes);

                    // Supprimer les valeurs sous les en-têtes identifiants
                    dataFlyProcessor.suppressIdentifyingAttributes(data, entetes);

                    // Afficher les données traitées dans la JTable tableRight
                    afficherDonneesTraitees(data, entetes);
                }
            }
        });
        box1.add(view);
        
        
        box1.add(anonymized);
        box1.add(personalisize);
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
        tableLeft.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Désactive le redimensionnement automatique des colonnes
        JScrollPane scrollPaneLeft = new JScrollPane(tableLeft, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Active seulement la barre de défilement horizontale
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
        tableRight.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Désactive le redimensionnement automatique des colonnes
        JScrollPane scrollPaneRight = new JScrollPane(tableRight, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Active seulement la barre de défilement horizontale
        this.add(scrollPaneRight);
    }

    /*
     * 
     *  Méthode pour gérer l'affichage des données
     * Les données sont tabulaire alors on utilise une liste de liste
     * pour chaque fiché chargé on ajoute une colonne supplémentaire pour la numerotation des valeurs
     *
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
         tableLeft.setModel(model); // Affichage des données dans la table de gauche
       
     }
   
     public void afficherDonnee(List<List<String>> data) {
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
       tableRight.setModel(model); // Affichage des données dans la table de gauche
     
   }
   
// Méthode pour afficher la fenêtre de dialogue de sélection de valeur
  /* public static String showValueSelectionDialog(JFrame parentFrame) {
       String[] values = {"3", "5", "7"}; // Remplace ce tableau par tes propres valeurs
       return (String) JOptionPane.showInputDialog(
               parentFrame,
               "Choose a value for K:",
               "Choose K",
               JOptionPane.PLAIN_MESSAGE,
               null,
               values,
               values[0]); // Valeur par défaut
   }*/
   
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
