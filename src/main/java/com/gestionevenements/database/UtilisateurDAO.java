package com.gestionevenements.database;

import com.gestionevenements.models.Utilisateur;
import java.sql.*;

public class UtilisateurDAO {

    public Utilisateur trouverParEmail(String email) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Créer et retourner l'objet Utilisateur
                // Note: Ceci est une version simplifiée, vous devrez gérer les différents types d'utilisateurs
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                ) {};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRoleUtilisateur(String email) {
        String query = "SELECT role FROM utilisateurs WHERE email = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

