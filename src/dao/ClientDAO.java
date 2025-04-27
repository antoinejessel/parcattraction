package dao;

import modele.Client;
import util.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ClientDAO {

    public static boolean insert(Client client) {
        String sql = "INSERT INTO Client (nom, email, mot_de_passe, date_naissance, type_client, points_fidelite) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getMotDePasse());
            ps.setDate(4, client.getDateNaissance());
            ps.setString(5, client.getTypeClient());
            ps.setInt(6, client.getPointsFidelite());

            ps.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Email déjà utilisé : " + client.getEmail());
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Client findByEmail(String email) {
        String sql = "SELECT * FROM Client WHERE email = ?";
        try (Connection con = ConnexionBDD.getConnexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

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
            e.printStackTrace();
        }
        return null;
    }
}
