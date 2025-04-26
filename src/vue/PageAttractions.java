package vue;

import dao.AttractionDAO;
import modele.Attraction;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PageAttractions extends JFrame {

    private Client clientConnecte;
    private AttractionDAO attractionDAO = new AttractionDAO();

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

        List<Attraction> attractions = attractionDAO.findAll();
        for (Attraction attraction : attractions) {
            if (attraction.isActif()) {
                panelGlobal.add(creerCarteAttraction(
                        attraction.getNom(),
                        attraction.getDescription(),
                        attraction.getImagePath(),
                        attraction.getIdAttraction(),
                        attraction.getPrix()
                ));
                panelGlobal.add(Box.createVerticalStrut(10));
            }
        }
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

        carte.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new PageReservation(nom, imagePath, description, idAttraction, prix, clientConnecte).setVisible(true);
            }
        });

        return carte;
    }
}
