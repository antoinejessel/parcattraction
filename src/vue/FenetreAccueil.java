package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreAccueil extends JFrame {

    private JButton btnInscription;
    private JButton btnConnexion;
    private JButton btnInvite;

    public FenetreAccueil() {
        setTitle("Bienvenue au Parc d'Attractions");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel labelTitre = new JLabel("Bienvenue dans notre Parc", SwingConstants.CENTER);
        labelTitre.setFont(new Font("SansSerif", Font.BOLD, 26));
        labelTitre.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        add(labelTitre, BorderLayout.NORTH);

        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new GridLayout(3, 1, 20, 20));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        btnInscription = creerBouton("Inscription (Nouveau client)", new Color(52, 152, 219));
        btnConnexion = creerBouton("Connexion (Client ou Admin)", new Color(46, 204, 113));
        btnInvite = creerBouton("Connexion rapide (Invité)", new Color(155, 89, 182));

        panelBoutons.add(btnInscription);
        panelBoutons.add(btnConnexion);
        panelBoutons.add(btnInvite);

        add(panelBoutons, BorderLayout.CENTER);

        btnInscription.addActionListener(e -> {
            new FenetreInscription().setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreAccueil::new);
    }
}
