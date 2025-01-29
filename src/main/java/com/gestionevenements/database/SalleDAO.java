package com.gestionevenements.database;

import com.gestionevenements.models.Salle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {

    public List<Salle> getTout() {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM salles";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                salles.add(new Salle(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("capacite")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salles;
    }
}

