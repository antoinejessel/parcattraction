package vue;

import dao.AttractionDAO;
import modele.Attraction;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PageAttractions extends JFrame {

    private Client clientConnecte;
    private AttractionDAO attractionDAO;

    public PageAttractions(Client client) {
        this.clientConnecte = client;
        this.attractionDAO = new AttractionDAO();

        setTitle("Bienvenue au Parc d'Attractions");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelGlobal = new JPanel();
        panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.Y_AXIS));
        panelGlobal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panelGlobal);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        List<Attraction> attractions = attractionDAO.findAll();
        for (Attraction attraction : attractions) {
            panelGlobal.add(creerCarteAttraction(attraction));
            panelGlobal.add(Box.createVerticalStrut(20));
        }

        JButton btnRetour = new JButton("Retour à l'accueil");
        btnRetour.setBackground(new Color(52, 152, 219));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFocusPainted(false);
        btnRetour.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRetour.addActionListener(e -> {
            new FenetreAccueil().setVisible(true);
            dispose();
        });

        JPanel footer = new JPanel();
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footer.add(btnRetour);

        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel creerCarteAttraction(Attraction attraction) {
        JPanel carte = new JPanel(new BorderLayout());
        carte.setBorder(BorderFactory.createTitledBorder(attraction.getNom()));
        carte.setMaximumSize(new Dimension(800, 250));
        carte.setBackground(Color.WHITE);
        carte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel imageLabel;
        if (attraction.getImage() != null) {
            ImageIcon icon = new ImageIcon(attraction.getImage());
            Image img = icon.getImage().getScaledInstance(250, 180, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(img));
        } else {
            imageLabel = new JLabel("Image non trouvée");
        }
        carte.add(imageLabel, BorderLayout.WEST);

        JTextArea textArea = new JTextArea(attraction.getDescription());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        carte.add(textArea, BorderLayout.CENTER);

        carte.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new PageReservation(attraction.getNom(), attraction.getImage(), attraction.getDescription(), attraction.getIdAttraction(), attraction.getPrix(), clientConnecte).setVisible(true);
                dispose();
            }
        });

        return carte;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setNom("Test");
        client.setEmail("test@test.com");
        client.setMotDePasse("1234");
        client.setTypeClient("membre");
        client.setPointsFidelite(0);

        SwingUtilities.invokeLater(() -> new PageAttractions(client).setVisible(true));
    }
}
