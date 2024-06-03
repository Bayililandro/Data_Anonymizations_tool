package test;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class MonTest {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			JWindow win= new JWindow();
			win.setSize(400, 600);
			win.setLocationRelativeTo(null);
			win.setVisible(true);
			
			JPanel panel= new JPanel();
			
			JProgressBar bar= new JProgressBar();
			win.add(bar);
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}	
		System.exit(0);
	}

}
