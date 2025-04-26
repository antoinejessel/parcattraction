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

    public PageReservation(String nom, String imagePath, String description, int idAttraction, double prix, Client client) {
        this.idAttraction = idAttraction;
        this.prixAttraction = prix;
        this.clientConnecte = client;

        setTitle("Réserver : " + nom);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Image
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            add(imageLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            add(new JLabel("Image non trouvée"), BorderLayout.NORTH);
        }

        // Description
        JTextArea area = new JTextArea(description);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setOpaque(false);
        add(area, BorderLayout.CENTER);

        // Bas
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());

        bottom.add(new JLabel("Choisissez une date :"));
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH)); // 🔥 Date par défaut = aujourd'hui
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        bottom.add(dateSpinner);

        JButton btnReserver = new JButton("Réserver");
        bottom.add(btnReserver);

        btnReserver.addActionListener(e -> {
            Date utilDate = (Date) dateSpinner.getValue();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            // 🔥 Vérifier si la date choisie est aujourd'hui ou dans le futur
            java.sql.Date aujourdHui = new java.sql.Date(System.currentTimeMillis());

            if (sqlDate.before(aujourdHui)) {
                JOptionPane.showMessageDialog(this,
                        "Vous ne pouvez pas réserver pour une date passée.",
                        "Date invalide", JOptionPane.WARNING_MESSAGE);
                return; // 🚫 Ne pas continuer si la date est dans le passé
            }

            // Sinon ➔ continuer la réservation
            boolean success = ReservationDAO.creerReservation(
                    clientConnecte.getId(), idAttraction, sqlDate, prixAttraction);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Réservation confirmée pour le " + sqlDate,
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la réservation.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(bottom, BorderLayout.SOUTH);
    }
}
