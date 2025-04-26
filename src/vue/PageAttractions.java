package vue;

import modele.Client;

import javax.swing.*;
import java.awt.*;

public class PageAttractions extends JFrame {

    private Client clientConnecte;

    public PageAttractions(Client client) {
        this.clientConnecte = client;

        setTitle("Bienvenue au Parc d'Attractions");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelGlobal = new JPanel();
        panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.Y_AXIS));
        panelGlobal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panelGlobal);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        // Cartes attractions
        panelGlobal.add(creerCarteAttraction(
                "Grand Huit", "Sensations fortes garanties !", "src/images/grand_huit.jpg", 1, 15.0));

        panelGlobal.add(Box.createVerticalStrut(10));

        panelGlobal.add(creerCarteAttraction(
                "Chaises Volantes", "Voler comme un oiseau !", "src/images/chaises_volantes.jpg", 2, 8.0));

        panelGlobal.add(Box.createVerticalStrut(10));

        panelGlobal.add(creerCarteAttraction(
                "Maison Hantée", "Oserez-vous y entrer ?", "src/images/maison_hantee.jpg", 3, 10.0));
    }

    private JPanel creerCarteAttraction(String nom, String description, String imagePath, int idAttraction, double prix) {
        JPanel carte = new JPanel(new BorderLayout());
        carte.setBorder(BorderFactory.createTitledBorder(nom));
        carte.setMaximumSize(new Dimension(750, 250));
        carte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        carte.setBackground(Color.WHITE);

        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            carte.add(imageLabel, BorderLayout.WEST);
        } catch (Exception e) {
            carte.add(new JLabel("Image non trouvée"), BorderLayout.WEST);
        }

        JTextArea textArea = new JTextArea(description);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        carte.add(textArea, BorderLayout.CENTER);

        // Clic sur carte
        carte.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new PageReservation(nom, imagePath, description, idAttraction, prix, clientConnecte).setVisible(true);
            }
        });

        return carte;
    }

    public static void main(String[] args) {
        // Simuler un client temporaire pour test
        modele.Client client = new modele.Client();
        client.setNom("Test");
        client.setEmail("test@test.com");
        client.setMotDePasse("1234");
        client.setTypeClient("membre");
        client.setPointsFidelite(0);

        SwingUtilities.invokeLater(() -> new PageAttractions(client).setVisible(true));
    }
}
