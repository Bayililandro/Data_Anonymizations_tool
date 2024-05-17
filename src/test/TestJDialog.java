package test;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestJDialog {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JDialog dialog= new JDialog();// Création de JDialog
				dialog.setTitle("Ma JDialog"); // Titré la JDialog
				dialog.setSize(500,600); // Définir la taille de la JDialog
				dialog.setLocationRelativeTo(null); // Centre le démarrage de la JDialog
				dialog.setVisible(true); // Rendre la JDialog visible
				dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Libéré les réssources de la JDialog une fois la croix cliqué
				
			}
		});

	}

}
