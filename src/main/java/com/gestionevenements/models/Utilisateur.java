package com.gestionevenements.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Utilisateur {
    protected final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    protected final StringProperty nom = new SimpleStringProperty(this, "nom");
    protected final StringProperty prenom = new SimpleStringProperty(this, "prenom");
    protected final StringProperty email = new SimpleStringProperty(this, "email");
    protected final StringProperty motDePasse = new SimpleStringProperty(this, "motDePasse");

    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse) {
        this.id.set(id);
        this.nom.set(nom);
        this.prenom.set(prenom);
        this.email.set(email);
        this.motDePasse.set(motDePasse);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getPrenom() { return prenom.get(); }
    public String getEmail() { return email.get(); }
    public String getMotDePasse() { return motDePasse.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    public void setEmail(String email) { this.email.set(email); }
    public void setMotDePasse(String motDePasse) { this.motDePasse.set(motDePasse); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty prenomProperty() { return prenom; }
    public StringProperty emailProperty() { return email; }
    public StringProperty motDePasseProperty() { return motDePasse; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}

