package com.gestionevenements.database;

import com.gestionevenements.models.DossierInscription;
import com.gestionevenements.models.Etudiant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DossierInscriptionDAO {

    private EtudiantDAO etudiantDAO;

    public DossierInscriptionDAO() {
        this.etudiantDAO = new EtudiantDAO();
    }

    public int ajouter(DossierInscription dossier) {
        String query = "INSERT INTO dossiers_inscription (etudiant_id, date_heure, filiere_interet, motivations, statut) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, dossier.getEtudiant().getId());
            stmt.setTimestamp(2, Timestamp.valueOf(dossier.getDateHeure()));
            stmt.setString(3, dossier.getFiliereInteret());
            stmt.setString(4, dossier.getMotivations());
            stmt.setString(5, dossier.getStatut());

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

    public boolean modifier(DossierInscription dossier) {
        String query = "UPDATE dossiers_inscription SET etudiant_id = ?, date_heure = ?, filiere_interet = ?, motivations = ?, statut = ? WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dossier.getEtudiant().getId());
            stmt.setTimestamp(2, Timestamp.valueOf(dossier.getDateHeure()));
            stmt.setString(3, dossier.getFiliereInteret());
            stmt.setString(4, dossier.getMotivations());
            stmt.setString(5, dossier.getStatut());
            stmt.setInt(6, dossier.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean supprimer(int id) {
        String query = "DELETE FROM dossiers_inscription WHERE id = ?";
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

    public List<DossierInscription> getTout() {
        List<DossierInscription> dossiers = new ArrayList<>();
        String query = "SELECT * FROM dossiers_inscription";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Etudiant etudiant = etudiantDAO.getById(rs.getInt("etudiant_id"));
                dossiers.add(new DossierInscription(
                        rs.getInt("id"),
                        etudiant,
                        rs.getTimestamp("date_heure").toLocalDateTime(),
                        rs.getString("filiere_interet"),
                        rs.getString("motivations"),
                        rs.getString("statut")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dossiers;
    }

    public DossierInscription getById(int id) {
        String query = "SELECT * FROM dossiers_inscription WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Etudiant etudiant = etudiantDAO.getById(rs.getInt("etudiant_id"));
                return new DossierInscription(
                        rs.getInt("id"),
                        etudiant,
                        rs.getTimestamp("date_heure").toLocalDateTime(),
                        rs.getString("filiere_interet"),
                        rs.getString("motivations"),
                        rs.getString("statut")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

