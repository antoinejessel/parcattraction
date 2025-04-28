package vue;

import modele.Client;

import javax.swing.*;
import java.awt.*;

public class FenetreInvite extends JFrame {

    public FenetreInvite() {
        setTitle("Bienvenue Invité");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Bienvenue en mode invité", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        JTextArea infos = new JTextArea(
                "En tant qu'invité, vous pouvez :\n\n" +
                        "- Consulter le catalogue des attractions\n" +
                        "- Voir les prix\n\n" +
                        "Certaines fonctionnalités sont désactivées :\n\n" +
                        "- Réservations\n" +
                        "- Historique\n" +
                        "- Réductions personnalisées"
        );
        infos.setEditable(false);
        infos.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infos.setOpaque(false);
        infos.setMargin(new Insets(10, 30, 10, 30));
        add(infos, BorderLayout.CENTER);

        JButton btnVoirCatalogue = creerBouton("Voir le catalogue", new Color(46, 204, 113));
        JButton btnRetour = creerBouton("Retour", new Color(52, 152, 219));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new GridLayout(1, 2, 20, 0));
        panelBoutons.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panelBoutons.add(btnVoirCatalogue);
        panelBoutons.add(btnRetour);

        add(panelBoutons, BorderLayout.SOUTH);

        btnVoirCatalogue.addActionListener(e -> {
            Client clientInvite = new Client();
            clientInvite.setNom("Invité");
            clientInvite.setEmail("invite@invite.com");
            clientInvite.setMotDePasse("");
            clientInvite.setTypeClient("invite");
            clientInvite.setPointsFidelite(0);

            new PageAttractions(clientInvite).setVisible(true);
            dispose();
        });

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreInvite::new);
    }
}
