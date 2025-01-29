package com.gestionevenements.database;

import com.gestionevenements.models.Stock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    public int ajouter(Stock stock) {
        String query = "INSERT INTO stocks (nom_produit, quantite, emplacement) VALUES (?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, stock.getNomProduit());
            stmt.setInt(2, stock.getQuantite());
            stmt.setString(3, stock.getEmplacement());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("L'ajout du stock a échoué, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("L'ajout du stock a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean modifier(Stock stock) {
        String query = "UPDATE stocks SET nom_produit = ?, quantite = ?, emplacement = ? WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, stock.getNomProduit());
            stmt.setInt(2, stock.getQuantite());
            stmt.setString(3, stock.getEmplacement());
            stmt.setInt(4, stock.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean supprimer(int id) {
        String query = "DELETE FROM stocks WHERE id = ?";
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

    public List<Stock> getTout() {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM stocks";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                stocks.add(new Stock(
                        rs.getInt("id"),
                        rs.getString("nom_produit"),
                        rs.getInt("quantite"),
                        rs.getString("emplacement")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public Stock getById(int id) {
        String query = "SELECT * FROM stocks WHERE id = ?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Stock(
                        rs.getInt("id"),
                        rs.getString("nom_produit"),
                        rs.getInt("quantite"),
                        rs.getString("emplacement")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

