package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreInvite extends JFrame {

    public FenetreInvite() {
        setTitle("Bienvenue Invité");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel labelTitre = new JLabel("Bienvenue en mode invité !", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 18));
        add(labelTitre, BorderLayout.NORTH);

        JTextArea infos = new JTextArea(
                "⚠️ En tant qu'invité, vous pouvez :\n" +
                        "- Consulter le catalogue des attractions\n" +
                        "- Voir les prix\n\n" +
                        "🔒 Certaines fonctions sont désactivées :\n" +
                        "- Réservations\n" +
                        "- Historique\n" +
                        "- Réductions personnalisées\n"
        );
        infos.setEditable(false);
        infos.setFont(new Font("Arial", Font.PLAIN, 14));
        add(infos, BorderLayout.CENTER);

        JButton btnVoirCatalogue = new JButton("Voir le catalogue");
        JButton btnRetour = new JButton("Retour");

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnVoirCatalogue);
        panelBoutons.add(btnRetour);
        add(panelBoutons, BorderLayout.SOUTH);

        btnVoirCatalogue.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Redirection vers la vue catalogue (simulée)...",
                    "Catalogue", JOptionPane.INFORMATION_MESSAGE);

        });

        btnRetour.addActionListener(e -> {
            new FenetreAccueil().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetreInvite().setVisible(true));
    }
}
