package interface_fenetre;


import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Fenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * On définit la taille, le nom de la fenêtre principale
	 * On imposer le démarrage de la fenêtre depuis le centre de l'écran
	 * liberer toute les réssources de la fenêtre une fois qu'elle est fermée
	 * Ici, on instancie également les composants de l'interface principale et on les ajoutes au conteneur de la fenêtre
	 * 
	 * */
	// intancier les menu et outil
			OutilEtMenu outilEtMenu= new OutilEtMenu(); // création proprement dite de la barre d'outil et de manu
	
	public Fenetre() {
		super("PFCL_Anonymization.Tool"); // nom de la fenetre
		this.setSize(800, 600); //taille de la fenetre au demarrage
		this.setLocationRelativeTo(null); // imposer la fenetre de demarrer au centre de l'ecran
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Liberer les ressource de la fenetre	 
		this.setJMenuBar(outilEtMenu.getMenuBar()); // recuperer la barre d'outil
		
		  JPanel jPanel=(JPanel)getContentPane();
		  jPanel.setBackground(Color.WHITE);
		
		  jPanel.add(outilEtMenu.getToolBar(), BorderLayout.NORTH); // recuperer la barre d'outil
		
		 // Création des zones verticales
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE); // Fond bleu pour la zone de gauche
        // Ajoutez les composants nécessaires à la zone de gauche ici

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE); // Fond rouge pour la zone de droite
        // Ajoutez les composants nécessaires à la zone de droite ici

        // Ajout des zones verticales à la fenêtre principale
       getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        this.add(new CadreRectangulaire());
      
		this.setVisible(true); // rendre la fenetre visible
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				int confirmButon= JOptionPane.showConfirmDialog(Fenetre.this, "Êtes-vous sûr de vouloir quitter?","Confirmation", JOptionPane.YES_NO_OPTION);
				if (confirmButon== JOptionPane.YES_NO_OPTION) {
					Fenetre.this.dispose();
				}
			}
		});
	}
	
}
