package dao;

import modele.Client;
import util.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ClientDAO {

    /**
     * Insère un nouveau client dans la base de données.
     * @param client L'objet Client à insérer.
     * @return true si l'insertion a réussi, false sinon.
     */
    public static boolean insert(Client client) {
        String sql = "INSERT INTO Client (nom, email, mot_de_passe, date_naissance, type_client, points_fidelite) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Remplit les paramètres de la requête
            ps.setString(1, client.getNom());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getMotDePasse());
            ps.setDate(4, client.getDateNaissance());
            ps.setString(5, client.getTypeClient());
            ps.setInt(6, client.getPointsFidelite());

            // Exécute la requête d'insertion
            ps.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Gère le cas où l'email est déjà utilisé
            System.err.println("Email déjà utilisé : " + client.getEmail());
            return false;

        } catch (SQLException e) {
            // Affiche une erreur en cas de problème avec la requête SQL
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recherche un client dans la base de données par son email.
     * @param email L'email du client à rechercher.
     * @return Un objet Client si trouvé, null sinon.
     */
    public static Client findByEmail(String email) {
        String sql = "SELECT * FROM Client WHERE email = ?";
        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Définit l'email comme paramètre de la requête
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            // Si un résultat est trouvé, crée un objet Client
            if (rs.next()) {
                return new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getDate("date_naissance"),
                        rs.getString("type_client"),
                        rs.getInt("points_fidelite")
                );
            }

        } catch (SQLException e) {
            // Affiche une erreur en cas de problème avec la requête SQL
            e.printStackTrace();
        }
        return null;
    }
}