package code_anonymisation_personnalisized;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import code_anonymisation_datafly.DialogUtils;
import interface_fenetre.CadreRectangulaire;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
public class CodeDataflyGen {
	
	  private static Set<String> identifierColumns = new HashSet<>();
	   private static Set<String> quasiIdentifierColumns = new HashSet<>();
	   private static Set<String> sensitiveColumns = new HashSet<>();
	
	// generalisation des attributs quasi-identifiants
	
	 private static final String[][] generalizeHierarchy = {
	            {"0-5", "6-10", "11-15", "16-20"}, // Niveau 2
	            {"0-5", "6-10", "11-15", "16-20"}, // Niveau 3
	            {"0-10", "11-20"}, // Niveau 4
	            {"Bas", "Moyen", "Haut"} // Niveau 4
	        };
	 
	// Méthode pour généraliser la moyenne en fonction du niveau
	 public static String generalizeMoyenne(double moyenne, int niveau) {
	     if (niveau >1 && niveau <= 3) {
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
				 if (parcourtVal.equalsIgnoreCase("MPCI") || parcourtVal.equalsIgnoreCase("SVT")) {
				        return "ST";
				    } else if (parcourtVal.equalsIgnoreCase("LM") || parcourtVal.equalsIgnoreCase("Histoire") || parcourtVal.equalsIgnoreCase("Geographie")) {
				        return "LSH";
				    } else if (parcourtVal.equalsIgnoreCase("mathematique") || parcourtVal.equalsIgnoreCase("physique") || parcourtVal.equalsIgnoreCase("chimie") || parcourtVal.equalsIgnoreCase("informatique")) {
				        return "MPCI";
				    }
				    return parcourtVal;
			   
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

			            // Ici, on calcul le nombre de valeurs uniques dans une colonne
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
	              
	              public  static int getColumnIndexByName(String columnName) {
	                  for (int i = 0; i < CadreRectangulaire.tableRight.getColumnCount(); i++) {
	                      if (CadreRectangulaire.tableRight.getColumnName(i).equals(columnName)) {
	                          return i;
	                      }
	                  }
	                  return -1;
	              }
	              
	              // Méthodes de traitement pour chaque type de colonne
	              public static void traiterIdentifiant(String columnName) {
	          	    // Récupérer l'index de la colonne à partir du nom de la colonne
	          	    int columnIndex = getColumnIndexByName(columnName);
	          	
	          	    if (columnIndex != -1) {
	          	        // Parcourir toutes les lignes de la table (sauf l'en-tête)
	          	        for (int row = 0; row < CadreRectangulaire.tableRight.getRowCount(); row++) {
	          	            // Remplacer la valeur de la cellule par "*"
	          	            CadreRectangulaire.tableRight.setValueAt("*", row, columnIndex);
	          	        }
	          	    }
	          	}  
	              public static void traiterQuasiIdentifiant(String columnName) {
	            	  int columnIndex = getColumnIndexByName(columnName);
	            	  int k= DialogUtils.getSelectedValue();
	            	    if (columnIndex != -1) {
	            	        if (columnName.equalsIgnoreCase("Sexe")) {
	            	            // Généraliser les valeurs dans la colonne "Sexe"
	            	            for (int row = 0; row < CadreRectangulaire.tableRight.getRowCount(); row++) {
	            	                String value = (String) CadreRectangulaire.tableRight.getValueAt(row, columnIndex);
	            	                String generalizedValue = generalizeSexe(value, k); // Changez le niveau selon vos besoins
	            	                CadreRectangulaire.tableRight.setValueAt(generalizedValue, row, columnIndex);
	            	            }
	            	        } else if (columnName.equalsIgnoreCase("Age")) {
	            	                // Niveau de généralisation (ajustez cette valeur selon vos besoins)
	            	                // Généraliser les valeurs dans la colonne "Age"
	            	                for (int row = 0; row < CadreRectangulaire.tableRight.getRowCount(); row++) {
	            	                    String value = (String) CadreRectangulaire.tableRight.getValueAt(row, columnIndex);
	            	                    try {
	            	                        double age = Double.parseDouble(value);
	            	                        String generalizedValue = generalizeAge(age, k);
	            	                        CadreRectangulaire.tableRight.setValueAt(generalizedValue, row, columnIndex);
	            	                    } catch (NumberFormatException e) {
	            	                        e.printStackTrace();
	            	                    }
	            	                }
	            	        } else if(columnName.equalsIgnoreCase("Lieu de naissance")) {
            	            	// Généraliser les valeurs dans la colonne "Lieu de naissance"
	            	        	for(int row= 0; row <CadreRectangulaire.tableRight.getRowCount(); row++) {
	            	        		String value= (String) CadreRectangulaire.tableRight.getValueAt(row, columnIndex);
	            	        		String generalizedValue= generalizedLieuDeNaissance(value);
	            	        		CadreRectangulaire.tableRight.setValueAt(generalizedValue, row, columnIndex);
	            	        	}
            	            } else if(columnName.equalsIgnoreCase("Parcours")) {
            	            	 // Généraliser les valeurs dans la colonne "Parcours"
            	                for (int row = 0; row < CadreRectangulaire.tableRight.getRowCount(); row++) {
            	                    String value = (String) CadreRectangulaire.tableRight.getValueAt(row, columnIndex);
            	                    String generalizedValue = generaliseParcourt(value);
            	                    CadreRectangulaire.tableRight.setValueAt(generalizedValue, row, columnIndex);
            	            	}
            	            } else if(columnName.equalsIgnoreCase("Moyenne")) {
            	            	for(int row= 0; row < CadreRectangulaire.tableRight.getRowCount(); row++) {
            	            		String value = CadreRectangulaire.tableRight.getValueAt(row, columnIndex).toString();
            	            		double moyenne = Double.parseDouble(value);
            	            		String generalizedValue= generalizeMoyenne(moyenne, k);
            	            		CadreRectangulaire.tableRight.setValueAt(generalizedValue, row, columnIndex);
            	            	}
            	            	
            	            }else if(columnName.equalsIgnoreCase("Niveau")) {
            	            	// Généralisation de l'attribut "Niveau"
            	            	for(int row= 0; row< CadreRectangulaire.tableRight.getRowCount(); row++) {
            	            		String value= (String)CadreRectangulaire.tableRight.getValueAt(row, columnIndex)/*.toString()*/;// conertir les données en String. possible avec un cast ou la méthode toString
            	            		String generalizedValue= generalizedNiveau(value);
            	            		CadreRectangulaire.tableRight.setValueAt(generalizedValue, row, columnIndex);
            	            	}
            	            }
            	            	
	            	    }
	              }


	              public static void traiterSensible(String columnName) {
	                  // Implémentez le traitement pour les colonnes sensibles
	                 // System.out.println("Traitement de la colonne sensible: " + columnName);
	              }

	           // Méthode pour traiter les colonnes en fonction de leur type
	              public static void traiterColonnes() {
	                  for (String columnName : identifierColumns) {
	                      traiterIdentifiant(columnName);
	                  }
	                  for (String columnName : quasiIdentifierColumns) {
	                      traiterQuasiIdentifiant(columnName);
	                  }
	                  for (String columnName : sensitiveColumns) {
	                      traiterSensible(columnName);
	                  }
	              }
	              
	              public static void highlightIdentifier(String columnName) {
	                  highlightColumn(columnName, Color.RED);
	                  identifierColumns.add(columnName);
	                  quasiIdentifierColumns.remove(columnName);
	                  sensitiveColumns.remove(columnName);
	              }

	              public static void highlightSensitive(String columnName) {
	                  highlightColumn(columnName, Color.GREEN);
	                  sensitiveColumns.add(columnName);
	                  identifierColumns.remove(columnName);
	                  quasiIdentifierColumns.remove(columnName);
	              }

	              public static void highlightQuasiIdentifier(String columnName) {
	                  highlightColumn(columnName, Color.YELLOW);
	                  quasiIdentifierColumns.add(columnName);
	                  identifierColumns.remove(columnName);
	                  sensitiveColumns.remove(columnName);
	              }

	           	// methode permettant de marquer les en-têtes des colonnes lors du click pour la personnalisation
	              public static void highlightColumn(String columnName, Color color) {
	                  TableColumnModel columnModel = CadreRectangulaire.tableRight.getColumnModel();
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
	                  CadreRectangulaire.tableRight.getTableHeader().repaint(); // Rafraîchir l'en-tête
	              }
}
