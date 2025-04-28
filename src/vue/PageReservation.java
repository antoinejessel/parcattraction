package vue;

import dao.ReservationDAO;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class PageReservation extends JFrame {

    private int idAttraction;
    private double prixAttraction;
    private Client clientConnecte;

    public PageReservation(String nom, byte[] imageData, String description, int idAttraction, double prix, Client client) {
        this.idAttraction = idAttraction;
        this.prixAttraction = prix;
        this.clientConnecte = client;

        setTitle("Réserver : " + nom);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("Réserver votre attraction", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imageLabel;
        if (imageData != null) {
            ImageIcon icon = new ImageIcon(imageData);
            Image img = icon.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(img));
        } else {
            imageLabel = new JLabel("Image non trouvée");
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea area = new JTextArea(description);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setEditable(false);
        area.setOpaque(false);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setMaximumSize(new Dimension(500, 100));
        area.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel reservationPanel = new JPanel();
        reservationPanel.setLayout(new FlowLayout());

        JLabel labelDate = new JLabel("Choisissez une date :");
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        dateSpinner.setPreferredSize(new Dimension(120, 25));

        JButton btnReserver = new JButton("Payer et Réserver");
        btnReserver.setBackground(new Color(46, 204, 113));
        btnReserver.setForeground(Color.WHITE);
        btnReserver.setFocusPainted(false);
        btnReserver.setFont(new Font("SansSerif", Font.BOLD, 16));

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBackground(new Color(52, 152, 219));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFocusPainted(false);
        btnRetour.setFont(new Font("SansSerif", Font.BOLD, 16));

        reservationPanel.add(labelDate);
        reservationPanel.add(dateSpinner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnReserver);
        buttonPanel.add(btnRetour);

        mainPanel.add(titre);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(imageLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(area);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(reservationPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);

        btnReserver.addActionListener(e -> {
            Date utilDate = (Date) dateSpinner.getValue();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            java.sql.Date aujourdHui = new java.sql.Date(System.currentTimeMillis());

            if (sqlDate.before(aujourdHui)) {
                JOptionPane.showMessageDialog(this, "Vous ne pouvez pas réserver pour une date passée.", "Date invalide", JOptionPane.WARNING_MESSAGE);
                return;
            }

            new PagePaiement(prixAttraction, idAttraction, clientConnecte, sqlDate).setVisible(true);
            dispose();
        });

        btnRetour.addActionListener(e -> {
            new PageAttractions(clientConnecte).setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
