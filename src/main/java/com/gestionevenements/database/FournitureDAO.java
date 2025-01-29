package com.gestionevenements.database;

import com.gestionevenements.models.Fourniture;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournitureDAO {

    public int ajouter(Fourniture fourniture) {
        String query = "INSERT INTO fournitures (libelle, description, quantite, fournisseur, prix) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fourniture.getLibelle());
            stmt.setString(2, fourniture.getDescription());
            stmt.setInt(3, fourniture.getQuantite());
            stmt.setString(4, fourniture.getFournisseur());
            stmt.setDouble(5, fourniture.getPrix());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de la fourniture a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La création de la fourniture a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean supprimer(int id) {
        String query = "DELETE FROM fournitures WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Fourniture> getTout() {
        List<Fourniture> fournitures = new ArrayList<>();
        String query = "SELECT * FROM fournitures";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                fournitures.add(new Fourniture(
                        rs.getInt("id"),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getInt("quantite"),
                        rs.getString("fournisseur"),
                        rs.getDouble("prix")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournitures;
    }
}

