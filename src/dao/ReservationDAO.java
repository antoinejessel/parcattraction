package dao;

import util.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReservationDAO {

    /**
     * Insère une réservation pour un créneau horaire précis.
     */
    public static boolean creerReservation(int idClient, int idAttraction, Timestamp dateHeure, double prixTotal) {
        String sql = "INSERT INTO reservation (id_client, id_attraction, date_heure_reservation, prix_total) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ps.setInt(2, idAttraction);
            ps.setTimestamp(3, dateHeure);
            ps.setDouble(4, prixTotal);

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL pendant la réservation : " + e.getMessage());
            return false;
        }
    }

    /**
     * Vérifie si un créneau est déjà réservé pour une attraction donnée.
     */
    public static boolean creneauDisponible(int idAttraction, Timestamp dateHeure) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE id_attraction = ? AND date_heure_reservation = ?";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAttraction);
            ps.setTimestamp(2, dateHeure);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // disponible si aucune réservation
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL lors de la vérification de disponibilité : " + e.getMessage());
        }

        return false;
    }
}
