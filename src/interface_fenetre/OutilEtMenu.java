package interface_fenetre;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import create_project.Create_project;


public class OutilEtMenu {


	private JToolBar toolBar;
	private JMenuBar menuBar;
	private Font font;
	public OutilEtMenu() {
		toolBar= new JToolBar(); // creation de la barre d'outil
		font= new Font(" TimesRoman ",Font.ROMAN_BASELINE,18);
		/*
		 *  ajout des elements a la barre d'outil
		 *
		 *Ajouter des elements dans la barre d'outil
		 *Ajouter des icones 
		 *Ajouter des mnemonic
		 *Ajouter des Menus déroulant
		 *
		 **/
				JButton newProjectButton = new JButton(new ImageIcon("C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/new.png"));
				newProjectButton .setToolTipText("File -> New Project");
				toolBar.add(newProjectButton );
				
				
				newProjectButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						new Create_project();
						
					}
				});
				
				JButton jbutton2= new JButton(new ImageIcon("C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/save.png"));
				jbutton2.setToolTipText("File -> Save");
				toolBar.add(jbutton2);
				JButton jbutton3= new JButton(new ImageIcon("C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/save_as.png"));
				jbutton3.setToolTipText("File -> Save as");
				toolBar.add(jbutton3);
				
				toolBar.addSeparator(); // Ajoute un séparateur
				
				JButton jbutton4= new JButton(new ImageIcon("C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/undo.png"));
				jbutton4.setToolTipText("File -> Import");
				toolBar.add(jbutton4);
				JButton jbutton5= new JButton(new ImageIcon("C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/redo.png"));
				jbutton5.setToolTipText("File -> Export");
				toolBar.add(jbutton5);
				
				toolBar.addSeparator(); // Ajoute un séparateur
				
				JButton jbutton6= new JButton(new ImageIcon("C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/about.png"));
				jbutton6.setToolTipText("File -> Help!");
				toolBar.add(jbutton6);
				
				
				menuBar= new JMenuBar(); // création de la barre de menu
				
				// ajout des element à la barre de menu
				JMenu menuFile= new JMenu("File");//creation d'une instance de menu
				menuFile.setFont(font);
				menuFile.setMnemonic('F');
				
				menuBar.add(menuFile);//ajouter un menu dans la barre de menu
				JMenu menusave= new JMenu("New Project");//creation d'une instance de menu
				menusave.setFont(font);
				menusave.setMnemonic('N');
				
				menuBar.add(menusave);//ajouter un menu dans la barre de menu
				JMenu openfile= new JMenu("     Open File");//creation d'une instance de menu
				openfile.setFont(font);
				openfile.setMnemonic('O');
				menuBar.add(openfile);//ajouter un menu dans la barre de menu
				JMenu methode= new JMenu("      Methode");//creation d'une instance de menu
				methode.setFont(font);
				methode.setMnemonic('M');
				menuBar.add(methode); //ajouter un menu dans la barre de menu
				JMenu help= new JMenu("       Help!?"); //creation d'une instance de menu
				help.setFont(font);
				help.setMnemonic('A');
				menuBar.add(help); //ajouter un menu dans la barre de menu
				
				JMenuItem menuItem= new JMenuItem("New ");//creation d'une instance de menu deroulant
				menuItem.setFont(font);
				menuItem.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/new.png" ) );
				menuFile.add(menuItem);
				
				menuFile.addSeparator();
				
				JMenuItem openProject= new JMenuItem("Open ");//creation d'une instance de menu deroulant
				openProject.setFont(font);
				openProject.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/open.png" ) );
				menuFile.add(openProject);
				JMenuItem enregistrerProject= new JMenuItem("Save");//creation d'une instance de menu deroulant
				enregistrerProject.setFont(font);
				enregistrerProject.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/save.png" ) );
				enregistrerProject.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
				menuFile.add(enregistrerProject);
				JMenuItem enregistrer_sous= new JMenuItem("Save as");//creation d'une instance de menu deroulant
				enregistrer_sous.setFont(font);
				enregistrer_sous.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/save_as.png" ) );
				menuFile.add(enregistrer_sous);
				/*
				 *  Création de séparateurs entre les élément de la barre d'outil
				 *  ou tout simplement regrouper les éléments de la barre d'outil en catégorie
				 * 
				 * */
				menuFile.addSeparator();
				
				JMenuItem enregistrer= new JMenuItem("Import");//creation d'une instance de menu deroulant
				enregistrer.setFont(font);
				enregistrer.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/undo.png") );
				menuFile.add(enregistrer);
				JMenuItem enregistrerSous= new JMenuItem("Export");//creation d'une instance de menu deroulant
				enregistrerSous.setFont(font);
				enregistrerSous.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/redo.png") );
				menuFile.add(enregistrerSous);
				
				/*
				 *  Création de séparateurs entre les élément de la barre d'outil
				 *  ou tout simplement regrouper les éléments de la barre d'outil en catégorie
				 * 
				 * */
				menuFile.addSeparator();
				
				JMenuItem aide= new JMenuItem("Help!");//creation d'une instance de menu deroulant
				aide.setFont(font);
				aide.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/about.png") );
				aide.setMnemonic( 'A' );
				menuFile.add(aide);
				JMenuItem quiter= new JMenuItem("Exit");//creation d'une instance de menu deroulant
				quiter.setFont(font);
				quiter.setIcon( new ImageIcon( "C:/Users/Landro.com/Desktop/Glossaire/Landri/icone/icons/exit.png") );
				quiter.setMnemonic( 'x' );
				quiter.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
				menuFile.add(quiter);
	}

	/*
	 * Ici, on défini une polique qui permet d'accéder au élément barre d'outil
	 * et de menu depuis n'importe que classe du programme
	 * 
	 * */
	public JToolBar getToolBar() {
		return toolBar;
	}
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	

}
