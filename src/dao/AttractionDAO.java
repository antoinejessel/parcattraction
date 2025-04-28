package dao;

import modele.Attraction;
import util.ConnexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDAO {

    private Connection connection;

    // Constructeur : Initialise la connexion à la base de données
    public AttractionDAO() {
        this.connection = ConnexionBDD.getConnexion();
    }

    /**
     * Récupère toutes les attractions de la base de données.
     * @return Une liste d'objets Attraction.
     */
    public List<Attraction> findAll() {
        List<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM attraction";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Parcourt les résultats et crée des objets Attraction
            while (rs.next()) {
                Attraction attraction = new Attraction(
                        rs.getInt("id_attraction"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getDouble("prix"),
                        rs.getString("description"),
                        rs.getBoolean("actif"),
                        rs.getBytes("image")
                );
                attractions.add(attraction);
            }
        } catch (SQLException e) {
            // Affiche une erreur en cas de problème avec la requête SQL
            e.printStackTrace();
        }
        return attractions;
    }

    /**
     * Insère une nouvelle attraction dans la base de données.
     * @param attraction L'objet Attraction à insérer.
     * @return true si l'insertion a réussi, false sinon.
     */
    public boolean insert(Attraction attraction) {
        String sql = "INSERT INTO attraction (nom, type, prix, description, actif, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Remplit les paramètres de la requête
            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getType());
            pstmt.setDouble(3, attraction.getPrix());
            pstmt.setString(4, attraction.getDescription());
            pstmt.setBoolean(5, attraction.isActif());
            pstmt.setBytes(6, attraction.getImage());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Affiche une erreur en cas de problème avec l'insertion
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Met à jour une attraction existante dans la base de données.
     * @param attraction L'objet Attraction contenant les nouvelles données.
     * @return true si la mise à jour a réussi, false sinon.
     */
    public boolean update(Attraction attraction) {
        String sql = "UPDATE attraction SET nom = ?, type = ?, prix = ?, description = ?, actif = ?, image = ? WHERE id_attraction = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Remplit les paramètres de la requête
            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getType());
            pstmt.setDouble(3, attraction.getPrix());
            pstmt.setString(4, attraction.getDescription());
            pstmt.setBoolean(5, attraction.isActif());
            pstmt.setBytes(6, attraction.getImage());
            pstmt.setInt(7, attraction.getIdAttraction());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Affiche une erreur en cas de problème avec la mise à jour
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime une attraction de la base de données.
     * @param idAttraction L'identifiant de l'attraction à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    public boolean delete(int idAttraction) {
        String sql = "DELETE FROM attraction WHERE id_attraction = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Définit l'identifiant de l'attraction à supprimer
            pstmt.setInt(1, idAttraction);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Affiche une erreur en cas de problème avec la suppression
            e.printStackTrace();
            return false;
        }
    }
}