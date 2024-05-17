package code_anonymisation_datafly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KanonymityProcess {
	
	/*
	  * Méthode pour appliquer le k-anonymat avec généralisation
	  *  
	 * */
	public void applyKAnonymity(List<List<String>> data, int k) {
		List<Integer> quasiIdentifyingColumns= identifyQuasiIdentifyingColumns(data.get(0));
		for(int columnIndex: quasiIdentifyingColumns) {
			generalizeAttribute(data, columnIndex, k);
		}
	}
	
	/*
	 * Méthode pour identifier les colonnes des attributs quasi-identifiants
	 *  
	 * */
	
	private List<Integer> identifyQuasiIdentifyingColumns(List<String> headers){
		List<Integer> quasiIdentifyingColumns= new ArrayList<>();
		DataFlyProcessor datafly= new DataFlyProcessor();
		for(int i= 0; i<headers.size(); i++) {
			if(datafly.isQuasiIdentifyingAttribut(headers.get(i)));
			quasiIdentifyingColumns.add(i);
		}
		return quasiIdentifyingColumns;
	}
	
	/*
	 * Méthode pour généraliser les données d'un attribut pour ateindre le k-anonymat
	 * 
	 * */
	private void generalizeAttribute(List<List<String>> data, int columnIndex, int k) {
		String header= data.get(0).get(columnIndex);
		if(header.equalsIgnoreCase("Age")) {
			generalizeAgeAttribute(data, columnIndex, k);
		}else if(header.equalsIgnoreCase("Moyenne")) {
			generalizeMoyenneAttribute(data, columnIndex, k);
		}
	}

	/*
	 *  Méthode pour généraliser l'attribut Age 
	 *1- Récupérer tout les ages de la colonne spécifié 
	 * */
	private void generalizeAgeAttribute(List<List<String>> data, int columnIndex, int k) {
		//1
		List<Integer> ages= new ArrayList<>();
		for(int i= 1; i<data.size(); i++) {
			String ageString= data.get(i).get(columnIndex);
			try {
				int age = Integer.parseInt(ageString);
				ages.add(age);
			}catch (NumberFormatException e) {
				// ignoré les case vides
			}
		}
		
		/*
		 * Trier les ages par ordre croissant
		 * */
		Collections.sort(ages);
		
		 /* Calculer le nombre de groupes nécessaires pour atteindre le k-anonymat */
		int numGroups= (int)Math.ceil((double)ages.size()/k);
		
		/*Calculer la taille du groupe*/
		int groupSize= (int)Math.ceil(( double)ages.size()/numGroups);
		
		/*généralisation des valeurs  de l'attribut age en les regroupant par intervale*/
		int startAge= ages.get(0);
		int endAge= startAge;
		for(int i= 0; i<ages.size();i++) {
			if(i % groupSize== 0 && i!= 0) {
				// Ajouter l'intervalle d'âges généralisé à chaque ligne de données
				for(int j= 1; j<data.size(); j++) {
					String currentAgeString= data.get(j).get(columnIndex);
					try {
						int currentAge = Integer.parseInt(currentAgeString);
						if(currentAge>= startAge && currentAge<= endAge) {
							 // Remplacer l'âge spécifique par l'intervalle d'âges généralisé
							data.get(j).set(columnIndex, "["+ startAge +" - "+ endAge+"]");
						}
					}catch (NumberFormatException e) {
						//ignorer les cases vides
					}
				}
			    startAge= endAge+i;
			}
			endAge++;
		}
		
		/*Gérer le dernier intervalle si nécéssaire*/
		if (endAge > startAge) {
			for(int j= 0; j< data.size(); j++) {// Commencer à l'indice 1 pour éviter l'en-tête
				String currentAgeString= data.get(j).get(columnIndex);
				try {
					int currentAge= Integer.parseInt(currentAgeString);
					if (currentAge>= startAge && currentAge<= endAge) {
						// Remplacer l'âge spécifique par l'intervalle d'âges généralisé
	                    data.get(j).set(columnIndex, "["+startAge + " - " + endAge+"]");
					}
				}catch (NumberFormatException e) {
					// ignorer les cases vides
				}
			}
		}
	}
	
	/*
	 *  Méthode pour généraliser l'attribut moyenne 
	 *1- Récupérer toutes les moyennes de la colonne spécifié 
	 * */
	public void generalizeMoyenneAttribute(List<List<String>> data, int columnIndex, int k) {
	    // 1. Collecte des valeurs de l'attribut "Moyenne"
	    List<Double> values = new ArrayList<>();
	    for (int i = 1; i < data.size(); i++) { // Commencer à 1 pour ignorer les en-têtes
	        String valueString = data.get(i).get(columnIndex);
	        try {
	            double value = Double.parseDouble(valueString);
	            values.add(value);
	        } catch (NumberFormatException e) {
	            // Ignorer les valeurs non numériques
	        }
	    }

	    // 2. Tri des valeurs par ordre croissant
	    Collections.sort(values);

	    // 3. Calcul du nombre de groupes nécessaire pour atteindre le k-anonymat
	    int numGroups = (int) Math.ceil((int) values.size() / k);

	    // 4. Calcul de la taille du groupe
	    int groupSize = (int) Math.ceil((int) values.size() / numGroups);

	    // 5. Généralisation des valeurs en regroupant les valeurs en intervalles
	    double startValue = values.get(0);
	    double endValue = startValue;
	    for (int i = 0; i < values.size(); i++) {
	        if (i % groupSize == 0 && i != 0) {
	            // Ajouter l'intervalle de valeurs généralisées à chaque ligne de données
	            for (int j = 1; j < data.size(); j++) { // Commencer à l'indice 1 pour éviter l'en-tête
	                String currentValueString = data.get(j).get(columnIndex);
	                try {
	                    double currentValue = Double.parseDouble(currentValueString);
	                    if (currentValue >= startValue && currentValue <= endValue) {
	                        // Remplacer la valeur spécifique par l'intervalle de valeurs généralisé
	                        data.get(j).set(columnIndex, "[" + startValue + " - " + endValue + "]");
	                    }
	                } catch (NumberFormatException e) {
	                    // Ignorer les valeurs non numériques
	                }
	            }
	            startValue = endValue + 0.01; // Ajouter une petite quantité pour éviter la répétition des mêmes valeurs
	        }
	        endValue += 0.01; // Ajouter une petite quantité pour éviter la répétition des mêmes valeurs
	    }

	    // 6. Gérer le dernier intervalle si nécessaire
	    if (endValue > startValue) {
	        for (int j = 1; j < data.size(); j++) {
	            String currentValueString = data.get(j).get(columnIndex);
	            try {
	                double currentValue = Double.parseDouble(currentValueString);
	                if (currentValue >= startValue && currentValue <= endValue) {
	                    // Remplacer la valeur spécifique par l'intervalle de valeurs généralisé
	                    data.get(j).set(columnIndex, "[" + startValue + " - " + endValue + "]");
	                }
	            } catch (NumberFormatException e) {
	                // Ignorer les cases vides ou non numériques
	            }
	        }
	    }
	}

}
