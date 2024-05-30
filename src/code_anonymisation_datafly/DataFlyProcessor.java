package code_anonymisation_datafly;

import java.util.ArrayList;
import java.util.List;

import code_anonymisation_personnalisized.CodeDataflyGen;
import interface_fenetre.Fenetre;



public class DataFlyProcessor {
	Fenetre f;
	
	int niveau= DialogUtils.getSelectedValue();
	//int niveau= DialogUtils.showValueSelectionDialog(f);
	  // Méthode pour verifier si un attribut est identifiant
	private boolean isIdentifyingAttribut(String header) {
		 /*
		  * 
		  *  Vérifiez si l'en-tête correspond à l'un des attributs spécifiés : nom, prénom, matricule, Gmail 
		  *   Retournez true si l'en-tête correspond à un attribut identifiant, sinon false.
           */
		
		return header.equalsIgnoreCase("Nom") ||
				 header.equalsIgnoreCase("nom") ||
				header.equalsIgnoreCase("Prenom") ||
				header.equalsIgnoreCase("prenom") ||
				header.equalsIgnoreCase("Email") || 
				header.equalsIgnoreCase("email") ||
				header.equalsIgnoreCase("Gmail") || 
				header.equalsIgnoreCase("gmail") ||
				header.equalsIgnoreCase("Matricule") ||
				header.equalsIgnoreCase("contact") ||
				header.equalsIgnoreCase("matricule") ||
				header.equalsIgnoreCase("num_cnib")||
				header.equalsIgnoreCase("Num_CNIB");
	}
	
	
	 // Méthode pour verifier si un attribut est sensible
		private boolean isSensitiveIdentifyingAttribut(String header) {
			 /*
			  * 
			  *  Vérifiez si l'en-tête correspond à l'un des attributs spécifiés : Resultat, Mention
			  *  
			  *   Retournez true si l'en-tête correspond à un attribut sensible, sinon false.
	           */
			
			return header.equalsIgnoreCase("Resultat") ||
					 header.equalsIgnoreCase("Mention");
		}
		
		
		 // méthode pour marquer les attributs quasi-identifiants au niveau des en-tête
		  public void markSensitiveIdentifyingAttribut(List<String> headers) {
			    // Parcourir les en-têtes pour trouver les attributs quasi-identifiants
			  List<Integer> quasiIdentifyingColumns = new ArrayList<>();
			    for (int i = 0; i < headers.size(); i++) {
			        String header = headers.get(i);
			        
			        // Verifier si l'en-tête correspond à un quasi-identifiant
			        if (isSensitiveIdentifyingAttribut(header)) {
			            // Si oui, ajouter un repère visuel
			            headers.set(i, "<html><font color='blue'>" + header + "</font> <font color='green'>●</font></html>");
			            quasiIdentifyingColumns.add(i);
			           
			        }
			    }
				
			}
		
	

	
	// méthode pour marquer les attributs identifiants au niveau des en-tête
		  public void markIdentifyingAttributes(List<String> headers) {
			  
			  // Parcourir les en-tête pour trouver les attributs identifiants
			     for(int i= 0; i< headers.size(); i++) {
			    	 String header= headers.get(i);
			    	 
			    	 // Verifier si l'attribut correspond à un identifant
			    	    if(isIdentifyingAttribut(header)) {
			    	    	// si oui, ajpouter un repère visuel
			    	    	 headers.set(i, "<html><font color='blue'>" + header + "</font> <font color='red'>●</font></html>");
			    	    	
			    	    }
			     }
		  }

		// Méthode utilitaire pour récupérer les indices des colonnes identifiantes
		    public List<Integer> getIdentifyingColumns(List<String> headers) {
		        List<Integer> identifyingColumns = new ArrayList<>();
		        for (int i = 0; i < headers.size(); i++) {
		            String header = headers.get(i);
		            if (isIdentifyingAttribut(header)) {
		                identifyingColumns.add(i); // Ajouter l'indice de colonne si l'en-tête est un attribut identifiant
		            }
		        }
		        return identifyingColumns;
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
        markQuasiIdentifyingAttributes(headers, data);
        markSensitiveIdentifyingAttribut(headers);
       
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
	public boolean isQuasiIdentifyingAttribut(String headers) {
		 // Vérifiez si l'en-tête correspond à l'un des attributs quasi-identifiants spécifiés
        // Retournez true si l'en-tête correspond à un attribut identifiant, sinon false.
		
		return headers.equalsIgnoreCase("Spécialité") ||
				headers.equalsIgnoreCase("spécialité") ||
				headers.equalsIgnoreCase("Note") ||
				headers.equalsIgnoreCase("Sexe") ||
				headers.equalsIgnoreCase("sexe") ||
				headers.equalsIgnoreCase("note") ||
				headers.equalsIgnoreCase("Moyenne") || 
				headers.equalsIgnoreCase("moyenne") ||
				headers.equalsIgnoreCase("Date de Naissance") || 
				headers.equalsIgnoreCase("date de naissance") ||
				headers.equalsIgnoreCase("Genre") || 
				headers.equalsIgnoreCase("genre") || 
				headers.equalsIgnoreCase("Code Postal")||
				headers.equalsIgnoreCase("pode postal")||
				headers.equalsIgnoreCase("Niveau")||
				headers.equalsIgnoreCase("niveau")||
				headers.equalsIgnoreCase("filière")||
				headers.equalsIgnoreCase("Filière")||
				headers.equalsIgnoreCase("Age")||
				headers.equalsIgnoreCase("age")||
				headers.equalsIgnoreCase("Parcours")||
				headers.equalsIgnoreCase("lieu de naissance")||
				headers.equalsIgnoreCase("Année D'inscription")||
				headers.equalsIgnoreCase("Année d'inscription");
		
	}
	
	 /* Méthode utilitaire pour récupérer les colonnes des données quasi-identifiants
	   * 1- Tableau pour stocker les index des colonnes quasi-identifiant
	   * 2- Ajouter l'indice de colonne si l'en-tête est un attribut quasi-identifiant
	   * 
	   * */
		@SuppressWarnings("unused")
		private List<Integer> getQuasiIdentifyingColumns(List<String> headers){
			  
			  List<Integer> quasiIdentifyingColumns= new ArrayList<>();//1
			  for(int i=0; i< headers.size(); i++) {
				  String header= headers.get(i);
				  if (isQuasiIdentifyingAttribut(header)) {
					  quasiIdentifyingColumns.add(i);//2
				  }
			  }
			  
			return quasiIdentifyingColumns;
			  
		  }
	
	
	        // méthode pour marquer les attributs quasi-identifiants au niveau des en-tête
		  public void markQuasiIdentifyingAttribut(List<String> headers) {
			    // Parcourir les en-têtes pour trouver les attributs quasi-identifiants
			  List<Integer> quasiIdentifyingColumns = new ArrayList<>();
			    for (int i = 0; i < headers.size(); i++) {
			        String header = headers.get(i);
			        
			        // Verifier si l'en-tête correspond à un quasi-identifiant
			        if (isQuasiIdentifyingAttribut(header)) {
			            // Si oui, ajouter un repère visuel
			            headers.set(i, "<html><font color='blue'>" + header + "</font> <font color='yellow'>●</font></html>");
			            quasiIdentifyingColumns.add(i);
			           
			        }
			    }
				
			}
		  
		// méthode pour marquer les attributs quasi-identifiants au niveau des en-tête
			public void markQuasiIdentifyingAttributes(List<String> headers, List<List<String>> data) {
			    // Parcourir les en-têtes pour trouver les attributs quasi-identifiants
			    List<Integer> quasiIdentifyingColumns = new ArrayList<>();
			    for (int i = 0; i < headers.size(); i++) {
			        String header = headers.get(i);
			        
			        // Vérifier si l'en-tête correspond à un quasi-identifiant
			        if (isQuasiIdentifyingAttribut(header)) {
			            // Si oui, ajouter un repère visuel
			            headers.set(i, "<html><font color='blue'>" + header + "</font> <font color='yellow'>●</font></html>");
			            quasiIdentifyingColumns.add(i);
			           
			            // Vérifier si l'en-tête correspond à "Moyenne" pour généraliser les valeurs
			            if (header.equalsIgnoreCase("Moyenne")) {
			                // Parcourir les données pour généraliser les valeurs de l'attribut "Moyenne"
			                for (List<String> row : data) {
			                   /* String moyenneValue = row.get(i);
			                    String generalizedMoyenne = generalizeMoyenne(moyenneValue);
			                    row.set(i, generalizedMoyenne);*/
			                	String moyenneValue = row.get(i);
			                    double moyenne = Double.parseDouble(moyenneValue);
			                    int niveau= DialogUtils.getSelectedValue();
			                    String generalizedMoyenne = CodeDataflyGen.generalizeMoyenne(moyenne, niveau);
			                    row.set(i, generalizedMoyenne);
			                } 
			               
			            }
			            if(header.equalsIgnoreCase("Age")) {
			            	
			                // Parcourir les données pour généraliser les valeurs de l'attribut "Âge"
			            	for (List<String> row : data) {
			            	    String ageValue = row.get(i);
			            	    double age = Double.parseDouble(ageValue);
			            	    //int niveau = DialogUtils.getSelectedValue(); // Obtenez le niveau de généralisation de l'interface utilisateur
			            	    int niveau= DialogUtils.getSelectedValue();
			            	    String generalizedAge = CodeDataflyGen.generalizeAge(age, niveau);
			            	    row.set(i, generalizedAge);
			            	}
			            }
			            if (header.equalsIgnoreCase("Parcours")) {
			                for (List<String> row : data) {
			                    String parcoursValue = row.get(i);
			                    String lowercaseParcoursValue = parcoursValue.toLowerCase();
			                    if ("MPCI".equalsIgnoreCase(parcoursValue) || "SVT".equalsIgnoreCase(parcoursValue)) {
			                        row.set(i, "ST");
			                    }
			                    if ("Histoire".equalsIgnoreCase(parcoursValue)|| "Geographie".equalsIgnoreCase(parcoursValue)|| "LM".equals(parcoursValue)) {
			                    	row.set(i, "LSH");
			                    }
			                    if ("mathematique".equals(lowercaseParcoursValue) || "physique".equals(lowercaseParcoursValue)|| "chimie".equals(lowercaseParcoursValue)|| "informatique".equals(lowercaseParcoursValue)) {
			                    	row.set(i, "MPCI");
			                    }
			                }
			            }
			             if (header.equalsIgnoreCase("Lieu de naissance")) {
			            	for(List<String> row : data) {
			            		String lieuDeNaissance= row.get(i);
			            		String generalisedLieuDeNaissance= CodeDataflyGen.generalizedLieuDeNaissance(lieuDeNaissance);
			            		row.set(i, generalisedLieuDeNaissance);
			            	}
			            }
			            if (header.equalsIgnoreCase("Niveau")) {
			            	for(List<String> row : data) {
			            		String niveau= row.get(i);
			            		String generalizedNiveau= CodeDataflyGen.generalizedNiveau(niveau);
			            		row.set(i, generalizedNiveau);
			            	}
			            }
			            if (header.equalsIgnoreCase("Sexe")) {
			            	for(List<String> row : data) {
			            		String sexe= row.get(i);
			            		int niveau= DialogUtils.getSelectedValue();
			            		String generalizedSexe= CodeDataflyGen.generalizeSexe(sexe, niveau);
			            		row.set(i, generalizedSexe);
			            	}
			            }
			        }
			    }
			}
}
