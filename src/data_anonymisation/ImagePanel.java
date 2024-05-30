package data_anonymisation;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;

    public ImagePanel() {
        try {
            // Load the image
            image = ImageIO.read(getClass().getResource("/ressources/images/fonds.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Draw the image to fill the entire panel
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}