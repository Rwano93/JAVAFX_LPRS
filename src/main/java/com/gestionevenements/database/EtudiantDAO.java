package com.gestionevenements.database;

import com.gestionevenements.models.Etudiant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {

    public int ajouter(Etudiant etudiant) {
        String query = "INSERT INTO etudiants (nom, prenom, email, mot_de_passe, dernier_diplome, telephone, adresse) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, etudiant.getNom());
            stmt.setString(2, etudiant.getPrenom());
            stmt.setString(3, etudiant.getEmail());
            stmt.setString(4, etudiant.getMotDePasse());
            stmt.setString(5, etudiant.getDernierDiplome());
            stmt.setString(6, etudiant.getTelephone());
            stmt.setString(7, etudiant.getAdresse());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de l'étudiant a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La création de l'étudiant a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean supprimer(int id) {
        String query = "DELETE FROM etudiants WHERE id = ?";
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

    public List<Etudiant> getTout() {
        List<Etudiant> etudiants = new ArrayList<>();
        String query = "SELECT * FROM etudiants";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                etudiants.add(new Etudiant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("dernier_diplome"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    public boolean modifier(Etudiant etudiant) {
        String query = "UPDATE etudiants SET nom = ?, prenom = ?, email = ?, dernier_diplome = ?, telephone = ?, adresse = ? WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, etudiant.getNom());
            stmt.setString(2, etudiant.getPrenom());
            stmt.setString(3, etudiant.getEmail());
            stmt.setString(4, etudiant.getDernierDiplome());
            stmt.setString(5, etudiant.getTelephone());
            stmt.setString(6, etudiant.getAdresse());
            stmt.setInt(7, etudiant.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Etudiant getById(int id) {
        String query = "SELECT * FROM etudiants WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Etudiant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("dernier_diplome"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

