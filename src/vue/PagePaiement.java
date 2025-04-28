package vue;

import dao.ReservationDAO;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class PagePaiement extends JFrame {

    private double prix;
    private int idAttraction;
    private Client clientConnecte;
    private Date dateReservation;

    public PagePaiement(double prix, int idAttraction, Client clientConnecte, Date dateReservation) {
        this.prix = prix;
        this.idAttraction = idAttraction;
        this.clientConnecte = clientConnecte;
        this.dateReservation = dateReservation;

        setTitle("Paiement sécurisé");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titre = new JLabel("Paiement sécurisé", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelMontant = new JLabel("Montant : " + prix + " €", SwingConstants.CENTER);
        labelMontant.setFont(new Font("SansSerif", Font.PLAIN, 18));
        labelMontant.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField champNumCarte = new JTextField();
        champNumCarte.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        champNumCarte.setAlignmentX(Component.CENTER_ALIGNMENT);
        champNumCarte.setBorder(BorderFactory.createTitledBorder("Numéro de carte (16 chiffres)"));

        JTextField champExpiration = new JTextField();
        champExpiration.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        champExpiration.setAlignmentX(Component.CENTER_ALIGNMENT);
        champExpiration.setBorder(BorderFactory.createTitledBorder("Expiration (MM/AA)"));

        JTextField champCVV = new JTextField();
        champCVV.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        champCVV.setAlignmentX(Component.CENTER_ALIGNMENT);
        champCVV.setBorder(BorderFactory.createTitledBorder("CVV (3 chiffres)"));

        JButton btnPayer = new JButton("Payer");
        btnPayer.setBackground(new Color(46, 204, 113));
        btnPayer.setForeground(Color.WHITE);
        btnPayer.setFocusPainted(false);
        btnPayer.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnPayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBackground(new Color(52, 152, 219));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFocusPainted(false);
        btnRetour.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnRetour.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titre);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(labelMontant);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(champNumCarte);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(champExpiration);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(champCVV);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(btnPayer);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnRetour);

        add(mainPanel, BorderLayout.CENTER);

        btnPayer.addActionListener(e -> {
            String numeroCarte = champNumCarte.getText().trim();
            String expiration = champExpiration.getText().trim();
            String cvv = champCVV.getText().trim();

            if (numeroCarte.isEmpty() || expiration.isEmpty() || cvv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir toutes les informations de paiement.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (numeroCarte.length() != 16 || !numeroCarte.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Numéro de carte invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!expiration.matches("\\d{2}/\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Date d'expiration invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cvv.length() != 3 || !cvv.matches("\\d{3}")) {
                JOptionPane.showMessageDialog(this, "CVV invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = ReservationDAO.creerReservation(
                    clientConnecte.getId(), idAttraction, dateReservation, prix);

            if (success) {
                JOptionPane.showMessageDialog(this, "Paiement réussi et réservation confirmée !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors du paiement.");
            }
        });

        btnRetour.addActionListener(e -> {
            new PageAttractions(clientConnecte).setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
