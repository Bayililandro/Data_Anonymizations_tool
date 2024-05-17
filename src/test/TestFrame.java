package test;

import javax.swing.SwingUtilities;

public class TestFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				FenetreJFrame fenetre= new FenetreJFrame();
				fenetre.setVisible(true);
				
			}
		});

	}

}
