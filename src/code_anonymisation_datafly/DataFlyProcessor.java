package code_anonymisation_datafly;

import java.util.ArrayList;
import java.util.List;


public class DataFlyProcessor {
	

	// méthode pour marquer les attributs identifiants au niveau des en-tête
	  public void markIdentifyingAttributes(List<String> headers) {
		  
		  // Parcourir les en-tête pour trouver les attributs identifiants
		     for(int i= 0; i< headers.size(); i++) {
		    	 String header= headers.get(i);
		    	 
		    	 // Verifier si l'attribut correspond à un identifant
		    	    if(isIdentifyingAttribut(header)) {
		    	    	// si oui, ajpouter un repère visuel
		    	    	 headers.set(i, "<html><font color='black'>" + header + "</font> <font color='red'>●</font></html>");
		    	    	
		    	    }
		     }
	  }

	  
	  // Méthode pour verifier si un attribut est identifiant
	private boolean isIdentifyingAttribut(String header) {
		 /*
		  * 
		  *  Vérifiez si l'en-tête correspond à l'un des attributs spécifiés : nom, prénom, matricule, Gmail
		  *  
		  *   Retournez true si l'en-tête correspond à un attribut identifiant, sinon false.
           */
		
		return header.equalsIgnoreCase("Nom") ||
				header.equalsIgnoreCase("Prenom") ||
				header.equalsIgnoreCase("Email") || 
				header.equalsIgnoreCase("Gmail") || 
				header.equalsIgnoreCase("Matricule") || 
				header.equalsIgnoreCase("Num_CNIB");
	}
	
	
	
	// Méthode pour supprimer les valeurs des attributs identifiants
    public void suppressIdentifyingAttributes(List<List<String>> data, List<String> headers) {
        // Récupérer les indices des colonnes identifiantes
        List<Integer> identifyingColumns = getIdentifyingColumns(headers);

     // Parcourir les lignes de données (en commençant à l'index 1 pour éviter les en-têtes)
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            // Supprimer les valeurs sous les en-têtes identifiants
            for (int columnIndex : identifyingColumns) {
                if (columnIndex >= 0 && columnIndex < row.size()) {
                    row.set(columnIndex, "*"); // Suppression de la valeur dans cette colonne pour chaque ligne de données
                }
            }
        }
        
        // Marquer toujours les en-têtes comme attributs identifiants
        markIdentifyingAttributes(headers);
        markQuasiIdentifyingAttributes(headers);
    }
  
    
    // Méthode utilitaire pour récupérer les indices des colonnes identifiantes
    private List<Integer> getIdentifyingColumns(List<String> headers) {
        List<Integer> identifyingColumns = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            if (isIdentifyingAttribut(header)) {
                identifyingColumns.add(i); // Ajouter l'indice de colonne si l'en-tête est un attribut identifiant
            }
        }
        return identifyingColumns;
    }
    
    /*
     * 
     * Traitement des attributs quasi-identifiants
     * 
     * Selectionner et les marquer de couleur jaune
     * 
     * faire une géneralisation à partir du k-anonymat
     * 
     * */
    
    
    
    // Méthode pour verifier si un attribut est quasi-identifiant
	private boolean isQuasiIdentifyingAttribut(String headers) {
		 // Vérifiez si l'en-tête correspond à l'un des attributs quasi-identifiants spécifiés
        // Retournez true si l'en-tête correspond à un attribut identifiant, sinon false.
		
		return headers.equalsIgnoreCase("Spécialité") ||
				headers.equalsIgnoreCase("Note") ||
				headers.equalsIgnoreCase("Moyenne") || 
				headers.equalsIgnoreCase("Date de naissance") || 
				headers.equalsIgnoreCase("Genre") || 
				headers.equalsIgnoreCase("Code postal")||
				headers.equalsIgnoreCase("Niveau")||
				headers.equalsIgnoreCase("filière")||
				headers.equalsIgnoreCase("Année d'inscription");
	}
	
	// méthode pour marquer les attributs quasi-identifiants au niveau des en-tête
		  public void markQuasiIdentifyingAttributes(List<String> headers) {
			    // Parcourir les en-têtes pour trouver les attributs quasi-identifiants
			    for (int i = 0; i < headers.size(); i++) {
			        String header = headers.get(i);
			        
			        // Verifier si l'en-tête correspond à un quasi-identifiant
			        if (isQuasiIdentifyingAttribut(header)) {
			            // Si oui, ajouter un repère visuel
			            headers.set(i, "<html><font color='black'>" + header + "</font> <font color='yellow'>●</font></html>");
			        }
			    }
			}

	
	
}
