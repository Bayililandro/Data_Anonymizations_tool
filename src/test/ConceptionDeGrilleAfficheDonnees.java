package test;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("serial")
public class ConceptionDeGrilleAfficheDonnees extends JFrame {


	private JTable table;
    private JButton loadButton;

    public ConceptionDeGrilleAfficheDonnees() {
        setTitle("Import File For Anonymize");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Utilisation de JScrollPane pour ajouter des barres de défilement
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        // Rendre Les colonnes non éditables
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scrollPane.setViewportView(table); // Ajout du tableau au JScrollPane

        // Definir un Ecouteur sur un bouton pour charger le fiché Excel depuis le local
        loadButton = new JButton("Import Excel File");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadExcelFile();
            }
        });
        add(loadButton, BorderLayout.SOUTH);
    }

    private void loadExcelFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers Excel", "xlsx", "xls"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                List<List<String>> data = readExcel(selectedFile);
                displayData(data);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier Excel : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // lecture du fiché Excel, et Extraction des données c'est à dire du contenu
    private List<List<String>> readExcel(File file) throws IOException {
        List<List<String>> data = new ArrayList<>();

        FileInputStream fileInput = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
        XSSFSheet sheet = workbook.getSheetAt(0); // Supposons que la première feuille est celle que vous souhaitez lire

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List<String> rowData = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                rowData.add(cell.toString());
            }
            data.add(rowData);
        }

        workbook.close();
        fileInput.close();

        return data;
    }
 
    private void displayData(List<List<String>> data) {
        int numRows = data.size();
        int numCols = data.get(0).size();

        // Créer un tableau de données avec une colonne supplémentaire pour le numéro de ligne
        String[][] rowData = new String[numRows - 1][numCols + 1];

        for (int i = 1; i < numRows; i++) { // Commencer à partir de la deuxième ligne
            List<String> row = data.get(i);
            
            // Ajouter le numéro de ligne à la première colonne
            rowData[i - 1][0] = String.valueOf(i);
            
            // Ajouter les données de la ligne à partir de la deuxième colonne
            for (int j = 0; j < numCols; j++) {
                rowData[i - 1][j + 1] = row.get(j);
            }
        }

        String[] columnNames = new String[numCols + 1];
        
        // Définir le nom de la première colonne comme "Numéro de ligne"
        columnNames[0] = "N°";
        
        // Utiliser les en-têtes de colonne du fichier Excel
        for (int i = 0; i < numCols; i++) {
            columnNames[i + 1] = data.get(0).get(i);
        }

        // Utilisation d'un modèle de tableau par défaut non éditable 
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table.setModel(model);
    }
    
    
    public static void main(String[] args) {
	    	ConceptionDeGrilleAfficheDonnees excelImporter = new ConceptionDeGrilleAfficheDonnees();
	    	excelImporter.setVisible(true);
	    }
}
