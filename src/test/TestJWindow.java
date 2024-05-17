package test;

import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class TestJWindow {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JWindow window= new JWindow();
				window.setSize(700, 500);
				window.setLocationRelativeTo(null);
				window.setVisible(true);
			}
		});

		try {
			Thread.sleep(5000);
		}catch (InterruptedException e) {
			System.exit(0);
		}
	}

}
