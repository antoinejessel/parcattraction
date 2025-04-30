package vue;

import controller.ReservationController;
import modele.Client;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class PagePaiement extends JFrame {

    private double prixTotal;
    private int idAttraction;
    private Client clientConnecte;
    private List<Timestamp> creneaux;

    public PagePaiement(double prixTotal, int idAttraction, Client clientConnecte, List<Timestamp> creneaux) {
        this.prixTotal = prixTotal;
        this.idAttraction = idAttraction;
        this.clientConnecte = clientConnecte;
        this.creneaux = creneaux;

        setTitle("Paiement sécurisé");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titre = new JLabel("Paiement sécurisé", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        final boolean promoAppliquee = clientConnecte.getAge() <= 25;
        final double prixFinal = promoAppliquee ? prixTotal * 0.70 : prixTotal;

        String texteMontant = "Montant total : " + String.format("%.2f", prixFinal) + " €";
        if (promoAppliquee) {
            texteMontant += " (✅ réduction -30%)";
        }
        JLabel labelMontant = new JLabel(texteMontant, SwingConstants.CENTER);
        labelMontant.setFont(new Font("SansSerif", Font.PLAIN, 18));
        labelMontant.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea recapCreneaux = new JTextArea();
        recapCreneaux.setEditable(false);
        recapCreneaux.setBackground(new Color(245, 245, 245));
        recapCreneaux.setFont(new Font("Monospaced", Font.PLAIN, 13));
        recapCreneaux.setBorder(BorderFactory.createTitledBorder("Créneaux sélectionnés"));

        StringBuilder recapText = new StringBuilder();
        for (Timestamp ts : creneaux) {
            recapText.append("• ").append(ts.toLocalDateTime().toLocalDate())
                    .append(" à ").append(ts.toLocalDateTime().toLocalTime()).append("\n");
        }
        recapCreneaux.setText(recapText.toString());

        JTextField champNumCarte = new JTextField();
        champNumCarte.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        champNumCarte.setBorder(BorderFactory.createTitledBorder("Numéro de carte (16 chiffres)"));

        JTextField champExpiration = new JTextField();
        champExpiration.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        champExpiration.setBorder(BorderFactory.createTitledBorder("Expiration (MM/AA)"));

        JTextField champCVV = new JTextField();
        champCVV.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        champCVV.setBorder(BorderFactory.createTitledBorder("CVV (3 chiffres)"));

        JButton btnPayer = new JButton("Payer");
        btnPayer.setBackground(new Color(46, 204, 113));
        btnPayer.setForeground(Color.WHITE);
        btnPayer.setFont(new Font("SansSerif", Font.BOLD, 16));

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBackground(new Color(52, 152, 219));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel boutons = new JPanel();
        boutons.add(btnPayer);
        boutons.add(Box.createHorizontalStrut(10));
        boutons.add(btnRetour);

        mainPanel.add(titre);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(labelMontant);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(recapCreneaux);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(champNumCarte);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(champExpiration);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(champCVV);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(boutons);

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

            double prixParCreneau = prixFinal / creneaux.size();
            boolean success = ReservationController.reserverCreneaux(clientConnecte.getId(), idAttraction, creneaux, prixParCreneau);

            if (success) {
                JOptionPane.showMessageDialog(this, "Paiement effectué et réservations enregistrées !");
                new PageAttractions(clientConnecte).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la réservation. Aucun créneau réservé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRetour.addActionListener(e -> {
            new PageAttractions(clientConnecte).setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}