package com.gestionevenements.models;

import javafx.beans.property.*;

public class Utilisateur {
    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty email;
    private final StringProperty motDePasse;
    private final StringProperty role;

    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.role = new SimpleStringProperty(role);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getPrenom() { return prenom.get(); }
    public String getEmail() { return email.get(); }
    public String getMotDePasse() { return motDePasse.get(); }
    public String getRole() { return role.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    public void setEmail(String email) { this.email.set(email); }
    public void setMotDePasse(String motDePasse) { this.motDePasse.set(motDePasse); }
    public void setRole(String role) { this.role.set(role); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty prenomProperty() { return prenom; }
    public StringProperty emailProperty() { return email; }
    public StringProperty motDePasseProperty() { return motDePasse; }
    public StringProperty roleProperty() { return role; }
}

