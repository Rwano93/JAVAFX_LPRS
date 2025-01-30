package com.gestionevenements.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Secretaire extends Utilisateur {
    private final StringProperty bureau = new SimpleStringProperty(this, "bureau");

    public Secretaire(int id, String nom, String prenom, String email, String motDePasse, String bureau) {
        super(id, nom, nom, prenom, email, motDePasse);
        this.bureau.set(bureau);
    }

    // Getter
    public String getBureau() { return bureau.get(); }

    // Setter
    public void setBureau(String bureau) { this.bureau.set(bureau); }

    // Property getter
    public StringProperty bureauProperty() { return bureau; }

    @Override
    public String toString() {
        return "Secretaire{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", bureau='" + getBureau() + '\'' +
                '}';
    }
}

