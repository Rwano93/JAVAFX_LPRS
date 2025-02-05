package com.gestionevenements.database;

import com.gestionevenements.models.RendezVous;
import com.gestionevenements.models.Salle;
import com.gestionevenements.models.Utilisateur;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public boolean supprimer(int id) {
        String query = "DELETE FROM rendez_vous WHERE id = ?";
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

    public List<RendezVous> getTout() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT r.*, s.nom as salle_nom, s.capacite as salle_capacite FROM rendez_vous r JOIN salles s ON r.salle_id = s.id";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Salle salle = new Salle(rs.getInt("salle_id"), rs.getString("salle_nom"), rs.getInt("salle_capacite"));
                RendezVous rendezVous = new RendezVous(
                        rs.getInt("id"),
                        rs.getString("etudiant"),
                        rs.getString("professeur"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("heure").toLocalTime(),
                        salle
                );
                rendezVousList.add(rendezVous);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }

    public List<RendezVous> getByEtudiant(String email) {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT r.*, s.nom as salle_nom, s.capacite as salle_capacite FROM rendez_vous r JOIN salles s ON r.salle_id = s.id WHERE r.etudiant = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Salle salle = new Salle(rs.getInt("salle_id"), rs.getString("salle_nom"), rs.getInt("salle_capacite"));
                RendezVous rendezVous = new RendezVous(
                        rs.getInt("id"),
                        rs.getString("etudiant"),
                        rs.getString("professeur"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("heure").toLocalTime(),
                        salle
                );
                rendezVousList.add(rendezVous);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }

    public List<RendezVous> getByProfesseur(String email) {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT r.*, s.nom as salle_nom, s.capacite as salle_capacite FROM rendez_vous r JOIN salles s ON r.salle_id = s.id WHERE r.professeur = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Salle salle = new Salle(rs.getInt("salle_id"), rs.getString("salle_nom"), rs.getInt("salle_capacite"));
                RendezVous rendezVous = new RendezVous(
                        rs.getInt("id"),
                        rs.getString("etudiant"),
                        rs.getString("professeur"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("heure").toLocalTime(),
                        salle
                );
                rendezVousList.add(rendezVous);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
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

    public List<Utilisateur> getAllEtudiants() {
        List<Utilisateur> etudiants = new ArrayList<>();
        String query = "SELECT * FROM utilisateurs WHERE role = 'ETUDIANT'";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                etudiants.add(new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    public List<Utilisateur> getAllProfesseurs() {
        List<Utilisateur> professeurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateurs WHERE role = 'PROFESSEUR'";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                professeurs.add(new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professeurs;
    }
}