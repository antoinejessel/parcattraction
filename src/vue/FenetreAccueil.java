package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreAccueil extends JFrame {

    private JButton btnInscription;
    private JButton btnConnexion;
    private JButton btnInvite;

    public FenetreAccueil() {
        setTitle("Bienvenue dans le Parc d'Attractions");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Titre
        JLabel labelTitre = new JLabel("Bienvenue !", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 20));
        add(labelTitre, BorderLayout.NORTH);

        // Boutons
        JPanel panelBoutons = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        btnInscription = new JButton("Inscription (Nouveau client)");
        btnConnexion = new JButton("Connexion (Client ou Admin)");
        btnInvite = new JButton("Connexion rapide (Invité)");

        panelBoutons.add(btnInscription);
        panelBoutons.add(btnConnexion);
        panelBoutons.add(btnInvite);

        add(panelBoutons, BorderLayout.CENTER);

        // Actions
        btnInscription.addActionListener(e -> {
            new FenetreInscription().setVisible(true); // c'est bien ta classe existante
            dispose();
        });

        btnConnexion.addActionListener(e -> {
            new FenetreConnexion().setVisible(true);
            dispose();
        });

        btnInvite.addActionListener(e -> {
            new FenetreInvite().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetreAccueil().setVisible(true));
    }
}
