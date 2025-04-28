package vue;

import dao.ClientDAO;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FenetreInscription extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nomField;
    private JTextField dateNaissanceField;
    private JButton btnInscription, btnRetour;
    private JLabel labelErreur;

    public FenetreInscription() {
        setTitle("Inscription");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Titre
        JLabel titre = new JLabel("Créer un compte", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        // Panel principal
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        nomField = new JTextField();
        nomField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        nomField.setBorder(BorderFactory.createTitledBorder("Nom"));

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setBorder(BorderFactory.createTitledBorder("Mot de passe"));

        dateNaissanceField = new JTextField();
        dateNaissanceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        dateNaissanceField.setBorder(BorderFactory.createTitledBorder("Date de naissance (yyyy-MM-dd)"));

        labelErreur = new JLabel("", SwingConstants.CENTER);
        labelErreur.setForeground(Color.RED);

        btnInscription = creerBouton("S'inscrire", new Color(46, 204, 113));
        btnRetour = creerBouton("Retour", new Color(52, 152, 219));

        // Ajout des composants
        panelForm.add(nomField);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(emailField);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(passwordField);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(dateNaissanceField);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(labelErreur);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(btnInscription);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(btnRetour);

        add(panelForm, BorderLayout.CENTER);

        btnInscription.addActionListener(e -> sInscrire());
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

    private void sInscrire() {
        // Récupération des champs
        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String motDePasse = new String(passwordField.getPassword()).trim();
        String dateNaissanceTexte = dateNaissanceField.getText().trim();

        // Vérification de remplissage
        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || dateNaissanceTexte.isEmpty()) {
            labelErreur.setText("Veuillez remplir tous les champs.");
            return;
        }

        try {
            // Vérification format date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date utilDate = sdf.parse(dateNaissanceTexte);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            // Vérification email déjà utilisé
            Client existingClient = ClientDAO.findByEmail(email);
            if (existingClient != null) {
                labelErreur.setText("Cet email est déjà utilisé.");
                return;
            }

            // Création du nouveau client
            Client client = new Client();
            client.setNom(nom);
            client.setEmail(email);
            client.setMotDePasse(motDePasse);
            client.setDateNaissance(sqlDate);
            client.setTypeClient("membre");
            client.setPointsFidelite(0);

            // Insertion
            boolean success = ClientDAO.insert(client);

            if (success) {
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                new FenetreConnexion().setVisible(true);
                dispose();
            } else {
                labelErreur.setText("Erreur technique lors de l'inscription.");
            }

        } catch (ParseException e) {
            labelErreur.setText("Format de date invalide (ex: 2000-01-31).");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreInscription::new);
    }
}
