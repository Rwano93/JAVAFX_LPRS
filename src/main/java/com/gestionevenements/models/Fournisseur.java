package com.gestionevenements.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fournisseur {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty nom = new SimpleStringProperty(this, "nom");
    private final StringProperty adresse = new SimpleStringProperty(this, "adresse");
    private final StringProperty telephone = new SimpleStringProperty(this, "telephone");
    private final StringProperty email = new SimpleStringProperty(this, "email");

    public Fournisseur(int id, String nom, String adresse, String telephone, String email) {
        this.id.set(id);
        this.nom.set(nom);
        this.adresse.set(adresse);
        this.telephone.set(telephone);
        this.email.set(email);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getAdresse() { return adresse.get(); }
    public String getTelephone() { return telephone.get(); }
    public String getEmail() { return email.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setAdresse(String adresse) { this.adresse.set(adresse); }
    public void setTelephone(String telephone) { this.telephone.set(telephone); }
    public void setEmail(String email) { this.email.set(email); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty adresseProperty() { return adresse; }
    public StringProperty telephoneProperty() { return telephone; }
    public StringProperty emailProperty() { return email; }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", adresse='" + getAdresse() + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}

