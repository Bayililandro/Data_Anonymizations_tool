package code_anonymisation_datafly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogUtils extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1197792937818176311L;
	private static int selectedValue = 1; // Valeur par défaut

    // Méthode statique pour afficher la fenêtre de dialogue de sélection de valeur
    public static int showValueSelectionDialog(JFrame parentFrame) {
        String[] values = {"2", "3", "4","5"}; // Valeur proposée aux utilisateurs dès le clic du bouton Choose K
        String selectedValueStr = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Choose k value:",
                "Choose k",
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
    
    // boite de dialogue qui permet un message de confirmation lorque les tables ne sont pas vide
    public int showConfirmDialog() {
        // Create a red question mark icon
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
        g.setColor(Color.RED);
        g.fillOval(0, 0, 32, 32);
        g.setColor(Color.WHITE);
        g.drawString("?", 12, 22);
        g.dispose();
        Icon icon = new ImageIcon(image);

        // Show the custom confirm dialog with the red question mark icon
        return JOptionPane.showConfirmDialog(this,
                "Table is not empty, are you sure to overwrite it",
                "Confirm Overwrite", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
    }
}
