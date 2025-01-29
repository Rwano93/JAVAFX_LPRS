package com.gestionevenements.database;

import com.gestionevenements.models.DossierInscription;
import java.sql.*;

public class DossierInscriptionDAO {

    public int ajouter(DossierInscription dossier) {
        String query = "INSERT INTO dossiers_inscription (etudiant_id, date_heure, filiere_interet, motivations) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, dossier.getEtudiant().getId());
            stmt.setTimestamp(2, Timestamp.valueOf(dossier.getDateHeure()));
            stmt.setString(3, dossier.getFiliereInteret());
            stmt.setString(4, dossier.getMotivations());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création du dossier d'inscription a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La création du dossier d'inscription a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}

