package test;

import javax.swing.JFrame;

public class FenetreJFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6456152416173071547L;

	public FenetreJFrame() {
		super();
		afficheJFrame();
	}
	
	public void afficheJFrame() {
		setTitle("MA_JFrame_De_Base");
		setSize(500, 600);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
