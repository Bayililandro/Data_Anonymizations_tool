package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Main extends JFrame {

    public Main() {
        super("Ma Fenêtre");

        // Création des boutons
        JButton createButton = new JButton("Créer Projet");
        JButton button2 = new JButton("Bouton 2");
        JButton button3 = new JButton("Bouton 3");

        // Désactivation des boutons sauf celui de création de projet
        button2.setEnabled(false);
        button3.setEnabled(false);

        // Ajout des boutons au conteneur
        JPanel panel = new JPanel();
        panel.add(createButton);
        panel.add(button2);
        panel.add(button3);

        // Ajout du conteneur à la fenêtre
        add(panel);

        // Définition de l'action du bouton de création de projet
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actions à effectuer lors de la création d'un projet

                // Activation de tous les boutons
                button2.setEnabled(true);
                button3.setEnabled(true);
            }
        });

        // Configuration de la fenêtre
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Création de l'instance de la fenêtre principale
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
