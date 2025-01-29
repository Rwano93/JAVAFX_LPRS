package com.gestionevenements.database;

import com.gestionevenements.models.Etudiant;
import java.sql.*;

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
}

