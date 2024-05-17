package code_anonymisation_datafly;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogUtils {

	private static int selectedValue = 1; // Valeur par défaut

    // Méthode statique pour afficher la fenêtre de dialogue de sélection de valeur
    public static int showValueSelectionDialog(JFrame parentFrame) {
        String[] values = {"2", "3", "4","5"}; // Valeur proposée aux utilisateurs dès le clic du bouton Choose K
        String selectedValueStr = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Choose a value for K:",
                "Choose K",
                JOptionPane.PLAIN_MESSAGE,
                null,
                values,
                Integer.toString(selectedValue)); // Utiliser la dernière valeur sélectionnée comme valeur par défaut

        // Convertir la valeur sélectionnée en entier
        try {
            int newValue = Integer.parseInt(selectedValueStr);
            selectedValue = newValue; // Mettre à jour la valeur sélectionnée
            return newValue;
        } catch (NumberFormatException e) {
            // En cas d'erreur, retourner la valeur par défaut ou gérer l'erreur selon les besoins
            e.printStackTrace();
            return selectedValue; // Retourner la dernière valeur sélectionnée
        }
    }

    // Getter pour récupérer la dernière valeur sélectionnée
    public static int getSelectedValue() {
        return selectedValue;
    }
}
