package test;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Projet_Calculatrice extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Projet_Calculatrice() {
		super(); // Appel du constructeur Parent
		build(); // Appel de la méthode d'initialisation de la fenêtre
	}
	
	// méthode d'initialisation de la fenêtre
	private void build() {
		setTitle("CALCULATRICE");// définition du titre de la fenêtre
		setSize(400, 200); // définition de la taille
		setResizable(true); // désactiver le rédimentionnement
		setLocationRelativeTo(null); // centrer la fenêtre au démarrage
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Libéré les ressources de la fenêtre
		setContentPane(buildContentePane());
	}
	
	private JPanel buildContentePane() {
		JPanel panel= new JPanel(); // Création d'un container panel
		panel.setLayout(new FlowLayout()); // Demander au panel d'utiliser du FlowLayout
		panel.setBackground(Color.WHITE);
		
		JButton btn= new JButton(new CalculAction(this, "Calucler"));
		panel.add(btn);
		JTextField textField= new JTextField();
		textField.setColumns(10);
		panel.add(textField);
		
		JLabel label= new JLabel("Résultat : pas encore calculé"); // Création de composant pour afficher le texte
		panel.add(label); // ajouter le composant au container
		
		return panel;
	}
}
