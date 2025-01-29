package com.gestionevenements.database;

import com.gestionevenements.models.RendezVous;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVousDAO {

    public int ajouter(RendezVous rendezVous) {
        String query = "INSERT INTO rendez_vous (etudiant, professeur, date, heure, salle_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, rendezVous.getEtudiant());
            stmt.setString(2, rendezVous.getProfesseur());
            stmt.setDate(3, Date.valueOf(rendezVous.getDate()));
            stmt.setTime(4, Time.valueOf(rendezVous.getHeure()));
            stmt.setInt(5, rendezVous.getSalle().getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création du rendez-vous a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("La création du rendez-vous a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean getSalleDisponible(int salleId, LocalDate date, LocalTime heure) {
        String query = "SELECT COUNT(*) FROM rendez_vous WHERE salle_id = ? AND date = ? AND heure = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, salleId);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setTime(3, Time.valueOf(heure));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

