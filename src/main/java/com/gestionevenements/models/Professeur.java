package com.gestionevenements.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Professeur extends Utilisateur {
    private final StringProperty specialite = new SimpleStringProperty(this, "specialite");

    public Professeur(int id, String nom, String prenom, String email, String motDePasse, String specialite) {
        super(id, nom, nom, prenom, email, motDePasse);
        this.specialite.set(specialite);
    }

    // Getter
    public String getSpecialite() { return specialite.get(); }

    // Setter
    public void setSpecialite(String specialite) { this.specialite.set(specialite); }

    // Property getter
    public StringProperty specialiteProperty() { return specialite; }

    @Override
    public String toString() {
        return "Professeur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", specialite='" + getSpecialite() + '\'' +
                '}';
    }
}

