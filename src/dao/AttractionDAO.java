package dao;

import modele.Attraction;
import util.ConnexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDAO {

    private Connection connection;

    public AttractionDAO() {
        this.connection = ConnexionBDD.getConnexion();
    }

    public List<Attraction> findAll() {
        List<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM attraction";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Attraction attraction = new Attraction(
                        rs.getInt("id_attraction"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getDouble("prix"),
                        rs.getString("description"),
                        rs.getBoolean("actif"),
                        rs.getString("image_path") // 🔥 On lit aussi l'image
                );
                attractions.add(attraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attractions;
    }

    public boolean insert(Attraction attraction) {
        String sql = "INSERT INTO attraction (nom, type, prix, description, actif, image_path) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getType());
            pstmt.setDouble(3, attraction.getPrix());
            pstmt.setString(4, attraction.getDescription());
            pstmt.setBoolean(5, attraction.isActif());
            pstmt.setString(6, attraction.getImagePath()); // 🔥 Nouvelle colonne
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Attraction attraction) {
        String sql = "UPDATE attraction SET nom = ?, type = ?, prix = ?, description = ?, actif = ?, image_path = ? WHERE id_attraction = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getType());
            pstmt.setDouble(3, attraction.getPrix());
            pstmt.setString(4, attraction.getDescription());
            pstmt.setBoolean(5, attraction.isActif());
            pstmt.setString(6, attraction.getImagePath()); // 🔥 Nouvelle colonne
            pstmt.setInt(7, attraction.getIdAttraction());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idAttraction) {
        String sql = "DELETE FROM attraction WHERE id_attraction = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idAttraction);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
