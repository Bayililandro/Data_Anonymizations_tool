package create_project;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import interface_fenetre.Fenetre;

@SuppressWarnings("serial")
public class Importer_Exporter extends JDialog {

	/*
	 * 
	 * Définir une Méthode pour lire le fiché selectionner dépuis le local
	 * 
	 * Définir une Méthode pour importer le fiché selectionner dépuis le local
	 * 
	 * Filtrer uniquement les fichés Excel lorsqu'on se trouve dans un repertoire donné
	 * 
	 * 
	 * */
	   public Importer_Exporter(Fenetre parent) {
	        super(parent, "Importation Excel", true);

	        pack();
	       setLocationRelativeTo(parent);// ou null en paramètre pour que la JDialog reste centrée
 
	    }

	    // Méthode pour lire le fiché selectionner dépuis le local
	    public List<List<String>> readExcel(File file) throws IOException {
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
	        dispose(); 

	        return data;
	    }
	    
	       // Méthode Pour Importer un Fiché Excel
	    public List<List<String>> importExcelFile() {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers Excel", "xlsx", "xls")); // Filtrer Uniquement les fichés
	                                                                                                  //Excel dans un repertoire local
	        int result = fileChooser.showOpenDialog(this);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            try {
	               return readExcel(selectedFile);
	            } catch (IOException ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Erreur lors de la lecture du fichier Excel : " + ex.getMessage(),
	                        "Erreur", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	        return null;
	    }
	    
	    /**
	     * Methode d'exportation de données 
	     * 
	     * */
	    public static void exportToExcel(JTable table, File file) throws IOException {
	        TableModel model = table.getModel();
	        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				XSSFSheet sheet = workbook.createSheet("Sheet1");

    // Create header row
				Row headerRow = sheet.createRow(0);
				for (int i = 0; i < model.getColumnCount(); i++) {
				    String columnName = model.getColumnName(i);
				    // Supprimer les balises HTML et récupérer le texte entre elles
				    String cleanedColumnName = columnName.replaceAll("<.*?>", "");
				    headerRow.createCell(i).setCellValue(cleanedColumnName);
				}

				// Create data rows
				for (int i = 0; i < model.getRowCount(); i++) {
				    Row row = sheet.createRow(i + 1);
				    for (int j = 0; j < model.getColumnCount(); j++) {
				        Object value = model.getValueAt(i, j);
				        if (value != null) {
				            if (value instanceof Number) {
				                row.createCell(j).setCellValue(((Number) value).doubleValue());
				            } else {
				                row.createCell(j).setCellValue(value.toString());
				            }
				        }
				    }
				}

				// Write workbook to file
				try (FileOutputStream fos = new FileOutputStream(file)) {
				    workbook.write(fos);
				}
			}
	    }
		
	}
