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
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        chargerDonnees();
        add(new GraphiquePanel());
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

            int x = 50;
            int yBase = getHeight() - 100;

            int barWidth = 50;
            int espace = 80;

            int maxReservations = reservationsParAttraction.values().stream().max(Integer::compareTo).orElse(1);

            for (Map.Entry<String, Integer> entry : reservationsParAttraction.entrySet()) {
                int hauteur = (int) ((double) entry.getValue() / maxReservations * 300);

                g.setColor(Color.BLUE);
                g.fillRect(x, yBase - hauteur, barWidth, hauteur);

                g.setColor(Color.BLACK);
                g.drawString(entry.getKey(), x, yBase + 20);
                g.drawString(String.valueOf(entry.getValue()), x, yBase - hauteur - 5);

                x += barWidth + espace;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReportingAttractions::new);
    }
}
