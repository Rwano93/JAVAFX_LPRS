package com.gestionevenements.database;

import com.gestionevenements.models.Fournisseur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDAO {

    public int ajouter(Fournisseur fournisseur) {
        String query = "INSERT INTO fournisseurs (nom, adresse, telephone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fournisseur.getNom());
            stmt.setString(2, fournisseur.getAdresse());
            stmt.setString(3, fournisseur.getTelephone());
            stmt.setString(4, fournisseur.getEmail());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création du fournisseur a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La création du fournisseur a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean modifier(Fournisseur fournisseur) {
        String query = "UPDATE fournisseurs SET nom = ?, adresse = ?, telephone = ?, email = ? WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fournisseur.getNom());
            stmt.setString(2, fournisseur.getAdresse());
            stmt.setString(3, fournisseur.getTelephone());
            stmt.setString(4, fournisseur.getEmail());
            stmt.setInt(5, fournisseur.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean supprimer(int id) {
        String query = "DELETE FROM fournisseurs WHERE id = ?";
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

    public List<Fournisseur> getTout() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String query = "SELECT * FROM fournisseurs";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                fournisseurs.add(new Fournisseur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }

    public Fournisseur getById(int id) {
        String query = "SELECT * FROM fournisseurs WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Fournisseur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

