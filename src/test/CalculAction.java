package test;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class CalculAction extends AbstractAction {

	Projet_Calculatrice fenetre;
	public CalculAction(Projet_Calculatrice fenetre, String texte) {
		super(texte);
		this.fenetre= fenetre;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
