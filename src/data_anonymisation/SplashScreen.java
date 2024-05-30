package data_anonymisation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class SplashScreen extends JWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private static final int WIDTH = 600;
        private static final int HEIGHT = 400;

        public SplashScreen() {
        	JPanel content = new ImagePanel();
            content.setLayout(new BorderLayout());
            content.setBackground(Color.WHITE);

            // Set the window's bounds, position the window at the center of the screen
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screen.width - WIDTH) / 2;
            int y = (screen.height - HEIGHT) / 2;
            setBounds(x, y, WIDTH, HEIGHT);

            // Create a loading animation
            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);
            content.add(progressBar, BorderLayout.SOUTH);

            // You can add a label or an image to the splash screen if you want
            JLabel label = new JLabel("Loading, please wait...", JLabel.CENTER);
            label.setForeground(Color.BLUE);
            label.setFont(new Font("Arial", Font.BOLD, 20)); // Change the font, style, and size here
            content.add(label, BorderLayout.CENTER);

            setContentPane(content);
        }
    }