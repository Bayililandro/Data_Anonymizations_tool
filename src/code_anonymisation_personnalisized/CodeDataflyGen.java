package code_anonymisation_personnalisized;
import javax.swing.JTable;
import java.util.HashSet;
import java.util.Set;
public class CodeDataflyGen {
	
	 private static final String[][] generalizeHierachy = {
	            {"0-5", "6-10", "11-15", "16-20"}, // Niveau 2
	            {"0-10", "11-20"}, // Niveau 3
	            {"Bas", "Moyen", "Haut"} // Niveau 4
	        };
	    
	    // Méthode pour généraliser la moyenne en fonction du niveau
	 public static String generalizeMoyenne(double moyenne, int niveau) {
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
		}

	 /*
	  *  Methode de généralisation de l'attribut Age
	  *  
	  * */

	 private static final String[][] generalizeAge = {
			    {"0-5", "6-10", "11-15", "16-20", "21-25", "26-30", "31-35", "36-40", "41-45", "46-50","51-55","56-60","61-65","66-70","71-75","76-80","81-85","86-90","91-95","96-100"}, // Niveau 2
			    {"0-10", "11-20", "21-30", "31-40", "41-50","51-60","61-70","71-80","81-90","91-100"}, // Niveau 3
			    {"Enfant", "Jeune", "Adulte", "Senior"} // Niveau 4
			};
	 
	 public static String generalizeAge(double age, int niveau) {
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
		}
	 
	 /*
	  *  Hiérarchie de généralisation de l'attribut sexe
	  *  
	  * */

	 private static final String[][] generalizeSexe = {
			    {"F , M"}, // Niveau 1
			    {"Tout_sexe"} // Niveau 2
			};

			public static String generalizeSexe(String sexe, int niveau) {
			    if (niveau == 1) {
			        return generalizeSexe[0][0]; // Retourne "Tout_sexe" pour tous les niveaux
			    } else if(niveau >= 2) {
			    	return generalizeSexe[1][0];
			    }
			    else {
			        return "#"; // Ou une autre valeur par défaut si nécessaire
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
