package create_project;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.io.File;

@SuppressWarnings("serial")
public class Create_project  extends JFrame {

	/*
	 * 
	 * Ici, le programme permet de créer un nouveau projet
	 * Création du conteneur principal avec GridBagLayout
	 * Permet au composant de remplir l'espace horizontalement
	 * Fermer la fenêtre après la création du projet
	 * 
	 * */
	    private JTextField projectNameField;
	    private JTextField projectLocationField;

	    public Create_project() {
	        setTitle("Nouveau Projet");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre

	        // Création du conteneur principal avec GridBagLayout
	        JPanel mainPanel = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants

	        JLabel projectNameLabel = new JLabel("Nom du projet:");
	        mainPanel.add(projectNameLabel, gbc);

	        gbc.gridx = 1;
	        gbc.fill = GridBagConstraints.HORIZONTAL; // Permet au composant de remplir l'espace horizontalement
	        projectNameField = new JTextField(20); // 20 colonnes pour le champ de texte
	        mainPanel.add(projectNameField, gbc);

	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        JLabel projectLocationLabel = new JLabel("Emplacement du projet:");
	        mainPanel.add(projectLocationLabel, gbc);

	        gbc.gridx = 1;
	        projectLocationField = new JTextField("C:\\Users\\Landro.com\\Desktop\\Projet_Java");
	        mainPanel.add(projectLocationField, gbc);

	        gbc.gridx = 2;
	        JButton browseButton = new JButton("Parcourir...");
	        browseButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Ouvrir une boîte de dialogue pour sélectionner un emplacement
	                JFileChooser fileChooser = new JFileChooser();
	                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                int returnValue = fileChooser.showOpenDialog(null);
	                if (returnValue == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    projectLocationField.setText(selectedFile.getAbsolutePath());
	                }
	            }
	        });
	        mainPanel.add(browseButton, gbc);

	        // Boutons "Créer" et "Annuler" dans un nouveau panneau
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        JButton createButton = new JButton("Créer");
	        createButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String projectName = projectNameField.getText();
	                String projectLocation = projectLocationField.getText();
	                createProject(projectName, projectLocation);
	            }
	        });
	        buttonPanel.add(createButton);

	        JButton cancelButton = new JButton("Annuler");
	        cancelButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                dispose(); // Fermer la fenêtre
	            }
	        });
	        buttonPanel.add(cancelButton);

	        // Ajouter le panneau de boutons à la fin du conteneur principal
	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        gbc.gridwidth = 3; // Étendre sur 3 colonnes
	        mainPanel.add(buttonPanel, gbc);

	        add(mainPanel);
	        pack(); // Redimensionner la fenêtre pour s'adapter aux composants
	        setLocationRelativeTo(null); // Centrer la fenêtre
	        setVisible(true);
	    }

	    private void createProject(String projectName, String projectLocation) {
	        // Vérifier si les champs sont vides
	        if (projectName.isEmpty() || projectLocation.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        // Créer le dossier du projet à l'emplacement spécifié
	        File projectDirectory = new File(projectLocation, projectName);
	        boolean created = projectDirectory.mkdirs();
	        if (created) {
	            JOptionPane.showMessageDialog(this, "Projet créé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
	            dispose(); // Fermer la fenêtre après la création du projet
	        } else {
	            JOptionPane.showMessageDialog(this, "Erreur lors de la création du projet", "Erreur", JOptionPane.ERROR_MESSAGE);
	        }
	    }


	
	}


