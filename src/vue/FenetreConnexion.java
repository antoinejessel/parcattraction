package vue;

import controller.ClientController;
import modele.Client;

import javax.swing.*;
import java.awt.*;

public class FenetreConnexion extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton btnConnexion, btnRetour;
    private JLabel labelErreur;

    public FenetreConnexion() {
        setTitle("Connexion");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Connexion Client/Admin", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setBorder(BorderFactory.createTitledBorder("Mot de passe"));

        labelErreur = new JLabel("", SwingConstants.CENTER);
        labelErreur.setForeground(Color.RED);

        btnConnexion = creerBouton("Se connecter", new Color(46, 204, 113));
        btnRetour = creerBouton("Retour", new Color(52, 152, 219));

        panelForm.add(emailField);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(passwordField);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(labelErreur);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(btnConnexion);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(btnRetour);

        add(panelForm, BorderLayout.CENTER);

        btnConnexion.addActionListener(e -> seConnecter());
        btnRetour.addActionListener(e -> {
            new FenetreAccueil().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JButton creerBouton(String texte, Color couleur) {
        JButton bouton = new JButton(texte);
        bouton.setBackground(couleur);
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("SansSerif", Font.BOLD, 16));
        return bouton;
    }

    private void seConnecter() {
        String email = emailField.getText().trim();
        String motDePasse = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || motDePasse.isEmpty()) {
            labelErreur.setText("Veuillez remplir tous les champs.");
            return;
        }

        Client client = ClientController.trouverParEmail(email);

        if (client != null) {
            if (client.getMotDePasse().trim().equals(motDePasse)) {
                if (client.getTypeClient().equals("admin")) {
                    new AdminGestionAttractions().setVisible(true);
                } else {
                    new PageAttractions(client).setVisible(true);
                }
                dispose();
            } else {
                labelErreur.setText("Mot de passe incorrect.");
            }
        } else {
            labelErreur.setText("Aucun compte trouvé pour cet email.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreConnexion::new);
    }
}