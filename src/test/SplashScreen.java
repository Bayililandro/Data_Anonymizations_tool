package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class SplashScreen extends JWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9006984379910306135L;
	private static final int Width= 600;
	private static final int Height= 400;
	
	public SplashScreen() {
		JPanel panel= new ImagePanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLUE);

		// definition de la taille de la fenêtre de démarrage appelée JWindow
		Dimension screen= Toolkit.getDefaultToolkit().getScreenSize();// obtenir la taille de l'ecran
		int x= (screen.width-Width)/2;
		int y= (screen.height-Height)/2;
		setBounds(x, y, Width, Height);
		
		// Ajouter une barre de progression 
		JProgressBar progressebar= new JProgressBar();
		progressebar.setInheritsPopupMenu(true);
		panel.add(progressebar, BorderLayout.SOUTH);
		
		// Mettre un texte qui donne un message
		   JLabel label= new JLabel("patienter svp ...");
		   label.setBackground(Color.BLUE);
		   panel.add(label, BorderLayout.CENTER);
		   setContentPane(panel);
	}
}
