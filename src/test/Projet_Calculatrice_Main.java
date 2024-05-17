package test;

import javax.swing.SwingUtilities;

public class Projet_Calculatrice_Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Projet_Calculatrice calculatrice= new Projet_Calculatrice();
				calculatrice.setVisible(true);
				
			}
		});
	}

}
