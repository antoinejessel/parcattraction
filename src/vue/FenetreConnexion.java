package vue;

import dao.ClientDAO;
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
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelForm.add(new JLabel("Email :"));
        emailField = new JTextField();
        panelForm.add(emailField);

        panelForm.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        panelForm.add(passwordField);

        labelErreur = new JLabel("");
        labelErreur.setForeground(Color.RED);
        panelForm.add(labelErreur);

        btnConnexion = new JButton("Connexion");
        panelForm.add(btnConnexion);

        add(panelForm, BorderLayout.CENTER);

        btnRetour = new JButton("Retour à l'accueil");
        add(btnRetour, BorderLayout.SOUTH);

        btnConnexion.addActionListener(e -> seConnecter());
        btnRetour.addActionListener(e -> {
            new FenetreAccueil().setVisible(true);
            dispose();
        });
    }

    private void seConnecter() {
        String email = emailField.getText().trim();
        String motDePasse = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || motDePasse.isEmpty()) {
            labelErreur.setText("Veuillez remplir tous les champs.");
            return;
        }

        Client client = ClientDAO.findByEmail(email);

        if (client != null) {
            String mdpBase = client.getMotDePasse().trim();

            if (mdpBase.equals(motDePasse)) {
                // ✅ Redirection selon le type de client
                if (client.getTypeClient().equalsIgnoreCase("admin")) {
                    new AdminGestionAttractions().setVisible(true); // CORRECTION ICI
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
        SwingUtilities.invokeLater(() -> new FenetreConnexion().setVisible(true));
    }
}
