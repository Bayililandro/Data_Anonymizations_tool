package test;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import data_anonymisation.SplashScreen;
import interface_fenetre.Fenetre;

public class TestJWindow {

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		SplashScreen screen= new SplashScreen();
		screen.setVisible(true);
		
		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				Thread.sleep(5000);
				return null;
			}
			
			@Override
			protected void done() {
				try {
					get();
					screen.setVisible(false);
					screen.dispose();
					
					SwingUtilities.invokeLater(()->{
						Fenetre fentre= new Fenetre();
						fentre.setVisible(true);
					});
					
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				
			}
			
		}.execute();;
	}

}
