package code_anonymisation_datafly;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogUtils {

	 // Méthode pour afficher la fenêtre de dialogue de sélection de valeur
    public static String showValueSelectionDialog(JFrame parentFrame) {
        String[] values = {"1","2", "3", "5", "7"}; // Valeur proposée au utilisateurs dès le clic du bouton choose k
        return (String) JOptionPane.showInputDialog(
                parentFrame,
                "Choose a value for K:",
                "Choose K",
                JOptionPane.PLAIN_MESSAGE,
                null,
                values,
                values[0]); // Valeur par défaut
    }
}
