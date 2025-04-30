package vue;

import modele.Client;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class PageReservation extends JFrame {

    private Client client;
    private int idAttraction;
    private double prixUnitaire;
    private JCheckBox[] creneauxCheckBoxes;
    private JSpinner dateSpinner;

    public PageReservation(String nomAttraction, byte[] imageData, String description, int idAttraction, double prixUnitaire, Client client) {
        this.client = client;
        this.idAttraction = idAttraction;
        this.prixUnitaire = prixUnitaire;

        setTitle("Réservation");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        JLabel titre = new JLabel("Réserver : " + nomAttraction, SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(titre, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        centerPanel.add(new JLabel("Date :"));
        centerPanel.add(dateSpinner);

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(new JLabel("Créneaux disponibles :"));

        JPanel creneauxPanel = new JPanel(new GridLayout(0, 3, 10, 5));
        String[] horaires = {
                "10:00", "10:30", "11:00", "11:30",
                "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30",
                "16:00", "16:30", "17:00", "17:30"
        };
        creneauxCheckBoxes = new JCheckBox[horaires.length];
        for (int i = 0; i < horaires.length; i++) {
            creneauxCheckBoxes[i] = new JCheckBox(horaires[i]);
            creneauxPanel.add(creneauxCheckBoxes[i]);
        }
        centerPanel.add(creneauxPanel);

        JButton btnReserver = new JButton("Valider les créneaux");
        btnReserver.addActionListener(e -> reserver());
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(btnReserver);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void reserver() {
        Date utilDate = (Date) dateSpinner.getValue();
        LocalDate selectedDate = utilDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        List<Timestamp> creneauxChoisis = new ArrayList<>();

        for (JCheckBox box : creneauxCheckBoxes) {
            if (box.isSelected()) {
                LocalTime heure = LocalTime.parse(box.getText());
                Timestamp ts = Timestamp.valueOf(selectedDate.atTime(heure));
                creneauxChoisis.add(ts);
            }
        }

        if (creneauxChoisis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins un créneau.");
            return;
        }

        double prixTotal = prixUnitaire * creneauxChoisis.size();

        // Redirection vers la page de paiement
        new PagePaiement(prixTotal, idAttraction, client, creneauxChoisis).setVisible(true);
        dispose();
    }
}
