package com.gestionevenements.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GestionnaireStock extends Utilisateur {
    private final StringProperty departement = new SimpleStringProperty(this, "departement");

    public GestionnaireStock(int id, String nom, String prenom, String email, String motDePasse, String departement) {
        super(id, nom, prenom, email, motDePasse);
        this.departement.set(departement);
    }

    // Getter
    public String getDepartement() { return departement.get(); }

    // Setter
    public void setDepartement(String departement) { this.departement.set(departement); }

    // Property getter
    public StringProperty departementProperty() { return departement; }

    @Override
    public String toString() {
        return "GestionnaireStock{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", departement='" + getDepartement() + '\'' +
                '}';
    }
}

