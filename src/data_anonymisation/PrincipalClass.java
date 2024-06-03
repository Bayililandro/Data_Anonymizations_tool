package data_anonymisation;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import interface_fenetre.Fenetre;

public class PrincipalClass {

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        // Create and show the splash screen
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);
       

        // Use SwingWorker to handle the delay and then show the main window
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                // Simulate loading process for 5 seconds
                Thread.sleep(5000);
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    get();
                    // Close the splash screen
                    splash.setVisible(false);
                    splash.dispose();

                    // Show the main application window
                    SwingUtilities.invokeLater(new Runnable(){
                        
						@Override
						public void run() {
							Fenetre fenetre = new Fenetre();
	                        fenetre.setVisible(true);
						}
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}