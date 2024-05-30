package code_anonymisation_personnalisized;
import javax.swing.JTable;


import java.util.HashSet;
import java.util.Set;
public class CodeDataflyGen {
	
	 private static final String[][] generalizeHierarchy = {
	            {"0-5", "6-10", "11-15", "16-20"}, // Niveau 2
	            {"0-5", "6-10", "11-15", "16-20"}, // Niveau 3
	            {"0-10", "11-20"}, // Niveau 4
	            {"Bas", "Moyen", "Haut"} // Niveau 4
	        };
	    // Méthode pour généraliser la moyenne en fonction du niveau
	/* public static String generalizeMoyenne(double moyenne, int niveau) {
		    if (niveau >= 1 && niveau <= 4) {
		        int index;
		        switch (niveau) {
		            case 1:
		                index = (int) Math.ceil(moyenne / 5) - 1;
		                if (index >= generalizeHierachy[0].length) {
		                    index = generalizeHierachy[0].length - 1;
		                }
		                return generalizeHierachy[0][index];
		            case 2:
		                index = (int) Math.ceil(moyenne / 10) - 1;
		                if (index >= generalizeHierachy[1].length) {
		                    index = generalizeHierachy[1].length - 1;
		                }
		                return generalizeHierachy[1][index];
		            case 3:
		                index = (int) Math.ceil(moyenne / 10) - 1;
		                if (index >= generalizeHierachy[1].length) {
		                    index = generalizeHierachy[1].length - 1;
		                }
		                return generalizeHierachy[1][index];
		            case 4:
		                index = (int) Math.ceil(moyenne / 20) - 1;
		                if (index >= generalizeHierachy[2].length) {
		                    index = generalizeHierachy[2].length - 1;
		                }
		                return generalizeHierachy[2][index];
		            default:
		                return "#";
		        }
		    } else {
		        return "#";
		    }
		}*/
	 
	// Méthode pour généraliser la moyenne en fonction du niveau
	 public static String generalizeMoyenne(double moyenne, int niveau) {
	     if (niveau >= 1 && niveau <= 3) {
	         switch (niveau) {
	             case 1:
	                 return findGeneralization(moyenne, generalizeHierarchy[0], 5);
	             case 2:
	                 return findGeneralization(moyenne, generalizeHierarchy[1], 10);
	             case 3:
	                 return findGeneralization(moyenne, generalizeHierarchy[2], 20);
	             default:
	                 return "#";
	         }
	     } else {
	         return "#";
	     }
	 }

	 private static String findGeneralization(double value, String[] intervals, int step) {
	     for (String interval : intervals) {
	         String[] bounds = interval.split("-");
	         if (bounds.length == 2) {
	             double lowerBound = Double.parseDouble(bounds[0]);
	             double upperBound = Double.parseDouble(bounds[1]);

	             if (value >= lowerBound && value <= upperBound) {
	                 return interval;
	             }
	         }
	     }
	     // Si la valeur n'est pas trouvée dans les intervalles, renvoyer l'intervalle correspondant au step
	     int index = (int) Math.ceil(value / step) - 1;
	     if (index >= intervals.length) {
	         index = intervals.length - 1;
	     }
	     return intervals[index];
	 }
	 

	 /*
	  *  Methode de généralisation de l'attribut Age
	  *  
	  * */

	 private static final String[][] generalizeAge = {
			    {"0-5", "6-10", "11-15", "16-20", "21-25", "26-30", "31-35", "36-40", "41-45", "46-50","51-55","56-60","61-65","66-70","71-75","76-80","81-85","86-90","91-95","96-100"}, // Niveau 2
			    {"0-5", "6-10", "11-15", "16-20", "21-25", "26-30", "31-35", "36-40", "41-45", "46-50","51-55","56-60","61-65","66-70","71-75","76-80","81-85","86-90","91-95","96-100"}, // Niveau 2
			    {"0-10", "11-20", "21-30", "31-40", "41-50","51-60","61-70","71-80","81-90","91-100"}, // Niveau 3
			    {"Enfant", "Jeune", "Adulte", "Senior"} // Niveau 4
			};
	 
	/* public static String generalizeAge(double age, int niveau) {
		    if (niveau >= 1 && niveau <= 4) {
		        int index;
		        switch (niveau) {
		            case 1:
		                index = (int) Math.ceil(age / 5) - 1;
		                if (index >= generalizeAge[0].length) {
		                    index = generalizeAge[0].length - 1;
		                }
		                return generalizeAge[0][index];
		            case 2:
	                    index = (int) Math.ceil(age / 10) - 1;
	                    if (index >= generalizeAge[1].length) {
	                        index = generalizeAge[1].length - 1;
	                    }
	                    return generalizeAge[1][index];
		            case 3:
		                index = (int) Math.ceil(age / 10) - 1;
		                if (index >= generalizeAge[1].length) {
		                    index = generalizeAge[1].length - 1;
		                }
		                return generalizeAge[1][index];
		            case 4:
		                index = (int) Math.ceil(age / 10) - 1;
		                if (index >= generalizeAge[2].length) {
		                    index = generalizeAge[2].length - 1;
		                }
		                return generalizeAge[2][index];
		            default:
		                return "#";
		        }
		    } else {
		        return "#";
		    }
		}*/
	 
	// Méthode pour généraliser l'âge en fonction du niveau
	 public static String generalizeAge(double age, int niveau) {
	     if (niveau >= 1 && niveau <= 3) {
	         switch (niveau) {
	             case 1:
	                 return findGeneralizationAge(age, generalizeAge[0], 5);
	             case 2:
	                 return findGeneralizationAge(age, generalizeAge[1], 10);
	             case 3:
	                 return findGeneralizationAge(age, generalizeAge[2], 20);
	             default:
	                 return "#";
	         }
	     } else {
	         return "#";
	     }
	 }

	 // Méthode auxiliaire pour trouver la généralisation appropriée
	 private static String findGeneralizationAge(double value, String[] intervals, int step) {
		    if (step > 0) {
		        for (String interval : intervals) {
		            String[] bounds = interval.split("-");
		            if (bounds.length == 2) {
		                double lowerBound = Double.parseDouble(bounds[0]);
		                double upperBound = Double.parseDouble(bounds[1]);
		                if (value >= lowerBound && value <= upperBound) {
		                    return interval;
		                }
		            }
		        }
		        int index = (int) Math.ceil(value / step) - 1;
		        if (index >= intervals.length) {
		            index = intervals.length - 1;
		        }
		        return intervals[index];
		    } else {
		        // Pour les intervalles non numériques comme les catégories (niveau 3)
		        if (value < 20) {
		            return intervals[0]; // Enfant
		        } else if (value < 40) {
		            return intervals[1]; // Jeune
		        } else if (value < 60) {
		            return intervals[2]; // Adulte
		        } else {
		            return intervals[3]; // Senior
		        }
		    }
	 }
	 
	 
	 /*
	  *  Hiérarchie de généralisation de l'attribut sexe
	  *  
	  * */

	 private static final String[][] generalizeSexe = {
			    {"F , M"}, // Niveau 1
			    {"M , F"}, // Niveau 1
			    {"Tout_sexe"} // Niveau 2
			};

	 public static String generalizeSexe(String sexe, int niveau) {
		    if (niveau >= 1 && niveau <= 3) {
		        switch (niveau) {
		            case 1:
		                // Vérifier si le sexe correspond à "F" ou "M"
		                for (String s : generalizeSexe[0]) {
		                    if (sexe.equalsIgnoreCase(s)) {
		                        return s;
		                    }
		                }
		                // Si aucune correspondance n'est trouvée, retourner une valeur par défaut
		                return generalizeSexe[0][0];
		            case 2:
		                return generalizeSexe[1][0]; // Retourne "Tout_sexe"
		            case 3:
		            	return generalizeSexe[2][0];
		            default:
		                return "#";
		        }
		    } else {
		        return "#";
		    }
		}
			
			 // Méthode pour généraliser les valeurs de l'attribut "Parcourt"
			public static String generaliseParcourt(String parcourtVal) {
				
				if (parcourtVal == "MPCI" || parcourtVal == "SVT") {
					
				}
			    // Généraliser toutes les valeurs de "Parcourt" par "ST"
			    return "ST";
			}
			
			/*
			 * généralisation de l'attribut lieu de naissance
			 * */
              public static String generalizedLieuDeNaissance(String naiss) {
            	  return "Burkina Faso";
              }

			
			/**
			 * Méthode qui aplique le k-anonymat
			 * */
			
			
			    // Méthode pour trouver la colonne avec le plus grand nombre de valeurs uniques
			    public static String findColumnWithMostUniqueValues(JTable table) {
			        // Initialisation des variables pour stocker le nombre maximal de valeurs uniques et le nom de la colonne correspondante
			        int maxUniqueValues = 0;
			        String columnWithMaxUniqueValues = null;

			        // Nombre total de colonnes et de lignes dans la JTable
			        int columnCount = table.getColumnCount();
			        int rowCount = table.getRowCount();

			        // Parcourir chaque colonne de la JTable
			        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			            // Utilisation d'un ensemble (HashSet) pour stocker les valeurs uniques de la colonne
			            Set<Object> uniqueValues = new HashSet<>();

			            // Parcourir chaque ligne de la colonne
			            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			                // Récupérer la valeur de la cellule à la ligne actuelle et dans la colonne actuelle
			                Object cellValue = table.getValueAt(rowIndex, columnIndex);
			                // Ajouter la valeur à l'ensemble des valeurs uniques
			                uniqueValues.add(cellValue);
			            }

			            // Calculer le nombre de valeurs uniques dans la colonne
			            int numUniqueValues = uniqueValues.size();
			            // Vérifier si ce nombre est supérieur au nombre maximal de valeurs uniques trouvé jusqu'à présent
			            if (numUniqueValues > maxUniqueValues) {
			                // Mettre à jour le nombre maximal de valeurs uniques et le nom de la colonne correspondante
			                maxUniqueValues = numUniqueValues;
			                columnWithMaxUniqueValues = table.getColumnName(columnIndex);
			            }
			        }

			        // Retourner le nom de la colonne avec le plus grand nombre de valeurs uniques
			        return columnWithMaxUniqueValues;
			    }
			    
			    /*
	               * Généralisation de l'attribut nivau
	               * 
	               * */
	              public static String generalizedNiveau(String niveauValue) {
	            	  return "Licence*";
	              }
			    
			
}
