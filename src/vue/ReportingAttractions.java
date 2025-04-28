package vue;

import util.ConnexionBDD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportingAttractions extends JFrame {

    private Map<String, Integer> reservationsParAttraction = new LinkedHashMap<>();

    public ReportingAttractions() {
        setTitle("Reporting des Attractions");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        chargerDonnees();

        GraphiquePanel graphiquePanel = new GraphiquePanel();
        add(graphiquePanel, BorderLayout.CENTER);

        JButton btnRetour = creerBouton("Retour à l'Admin Panel", new Color(52, 152, 219));
        JPanel panelBas = new JPanel();
        panelBas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBas.add(btnRetour);

        add(panelBas, BorderLayout.SOUTH);

        btnRetour.addActionListener(e -> {
            new AdminGestionAttractions().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void chargerDonnees() {
        String sql = "SELECT a.nom, COUNT(r.id_reservation) AS total " +
                "FROM reservation r " +
                "JOIN attraction a ON r.id_attraction = a.id_attraction " +
                "GROUP BY a.nom";

        try (Connection connection = ConnexionBDD.getConnexion();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                reservationsParAttraction.put(rs.getString("nom"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class GraphiquePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int margeGauche = 70;
            int margeBas = 100;
            int largeurBarre = 60;
            int espaceEntre = 80;

            int hauteurGraph = getHeight() - 2 * margeBas;
            int maxReservations = reservationsParAttraction.values().stream().max(Integer::compareTo).orElse(1);

            int x = margeGauche;

            for (Map.Entry<String, Integer> entry : reservationsParAttraction.entrySet()) {
                int hauteur = (int) ((double) entry.getValue() / maxReservations * hauteurGraph);

                g.setColor(new Color(52, 152, 219));
                g.fillRoundRect(x, getHeight() - margeBas - hauteur, largeurBarre, hauteur, 15, 15);

                g.setColor(Color.BLACK);
                g.setFont(new Font("SansSerif", Font.PLAIN, 12));
                g.drawString(entry.getKey(), x - 10, getHeight() - margeBas + 20);
                g.drawString(String.valueOf(entry.getValue()), x + 10, getHeight() - margeBas - hauteur - 10);

                x += largeurBarre + espaceEntre;
            }
        }
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
        SwingUtilities.invokeLater(ReportingAttractions::new);
    }
}
