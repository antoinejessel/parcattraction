package dao;

import util.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class ReservationDAO {

    /**
     * Crée une nouvelle réservation dans la base de données.
     * @param idClient L'identifiant du client effectuant la réservation.
     * @param idAttraction L'identifiant de l'attraction réservée.
     * @param dateReservation La date de la réservation.
     * @param prixTotal Le prix total de la réservation.
     * @return true si l'insertion a réussi, false sinon.
     */
    public static boolean creerReservation(int idClient, int idAttraction, Date dateReservation, double prixTotal) {
        // Requête SQL pour insérer une nouvelle réservation
        String sql = "INSERT INTO reservation (id_client, id_attraction, date_reservation, prix_total) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Remplit les paramètres de la requête
            ps.setInt(1, idClient);
            ps.setInt(2, idAttraction);
            ps.setDate(3, dateReservation);
            ps.setDouble(4, prixTotal);

            // Exécute la requête et vérifie si une ligne a été insérée
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Réservation insérée avec succès !");
                return true;
            } else {
                System.out.println("Aucune ligne insérée !");
                return false;
            }

        } catch (SQLException e) {
            // Gère les erreurs SQL et affiche un message d'erreur
            System.err.println("ERREUR SQL lors de la réservation : " + e.getMessage());
            return false;
        }
    }
}