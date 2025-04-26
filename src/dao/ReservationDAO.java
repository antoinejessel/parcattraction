package dao;

import util.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class ReservationDAO {

    public static boolean creerReservation(int idClient, int idAttraction, Date dateReservation, double prixTotal) {
        String sql = "INSERT INTO Reservation (id_client, id_attraction, date_reservation, prix_total) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ps.setInt(2, idAttraction);
            ps.setDate(3, dateReservation);
            ps.setDouble(4, prixTotal);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Réservation insérée avec succès !");
                return true;
            } else {
                System.out.println("❌ Aucune ligne insérée !");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ ERREUR SQL lors de la réservation : " + e.getMessage());
            return false;
        }
    }
}
