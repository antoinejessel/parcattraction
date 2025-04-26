package vue;

import dao.ClientDAO;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class FenetreInscription extends JFrame {

    private JTextField fieldNom, fieldEmail, fieldAge;
    private JPasswordField fieldMdp;
    private JButton btnInscrire, btnRetour;
    private JLabel labelMessage;

    public FenetreInscription() {
        setTitle("Inscription - Nouveau client");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel labelTitre = new JLabel("Création d’un compte client", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelTitre, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelForm.add(new JLabel("Nom :"));
        fieldNom = new JTextField();
        panelForm.add(fieldNom);

        panelForm.add(new JLabel("Email :"));
        fieldEmail = new JTextField();
        panelForm.add(fieldEmail);

        panelForm.add(new JLabel("Mot de passe :"));
        fieldMdp = new JPasswordField();
        panelForm.add(fieldMdp);

        panelForm.add(new JLabel("Âge :"));
        fieldAge = new JTextField();
        panelForm.add(fieldAge);

        labelMessage = new JLabel("");
        labelMessage.setForeground(Color.RED);
        panelForm.add(labelMessage);

        btnInscrire = new JButton("Créer un compte");
        panelForm.add(btnInscrire);

        add(panelForm, BorderLayout.CENTER);

        btnRetour = new JButton("Retour à l'accueil");
        add(btnRetour, BorderLayout.SOUTH);

        btnInscrire.addActionListener(e -> inscrireClient());
        btnRetour.addActionListener(e -> {
            new FenetreAccueil().setVisible(true);
            dispose();
        });
    }

    private void inscrireClient() {
        String nom = fieldNom.getText().trim();
        String email = fieldEmail.getText().trim();
        String mdp = new String(fieldMdp.getPassword()).trim();
        String ageText = fieldAge.getText().trim();

        if (nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || ageText.isEmpty()) {
            labelMessage.setText("Tous les champs sont obligatoires.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            if (age < 0 || age > 120) {
                labelMessage.setText("Âge invalide.");
                return;
            }

            Client client = new Client();
            client.setNom(nom);
            client.setEmail(email);
            client.setMotDePasse(mdp);
            client.setDateNaissance(new Date(System.currentTimeMillis())); // à remplacer si tu veux une date réelle
            client.setTypeClient("membre");
            client.setPointsFidelite(0);

            boolean success = ClientDAO.insert(client);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Inscription réussie ! Vous pouvez maintenant vous connecter.",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                new FenetreConnexion().setVisible(true);
                dispose();
            } else {
                labelMessage.setText("Email déjà utilisé !");
            }

        } catch (NumberFormatException ex) {
            labelMessage.setText("Âge doit être un entier.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetreInscription().setVisible(true));
    }
}
