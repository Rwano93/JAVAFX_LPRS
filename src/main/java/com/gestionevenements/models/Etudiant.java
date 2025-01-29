package com.gestionevenements.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Etudiant extends Utilisateur {
    private final StringProperty dernierDiplome = new SimpleStringProperty(this, "dernierDiplome");
    private final StringProperty telephone = new SimpleStringProperty(this, "telephone");
    private final StringProperty adresse = new SimpleStringProperty(this, "adresse");

    public Etudiant(int id, String nom, String prenom, String email, String motDePasse,
                    String dernierDiplome, String telephone, String adresse) {
        super(id, nom, prenom, email, motDePasse);
        this.dernierDiplome.set(dernierDiplome);
        this.telephone.set(telephone);
        this.adresse.set(adresse);
    }

    // Getters
    public String getDernierDiplome() { return dernierDiplome.get(); }
    public String getTelephone() { return telephone.get(); }
    public String getAdresse() { return adresse.get(); }

    // Setters
    public void setDernierDiplome(String dernierDiplome) { this.dernierDiplome.set(dernierDiplome); }
    public void setTelephone(String telephone) { this.telephone.set(telephone); }
    public void setAdresse(String adresse) { this.adresse.set(adresse); }

    // Property getters
    public StringProperty dernierDiplomeProperty() { return dernierDiplome; }
    public StringProperty telephoneProperty() { return telephone; }
    public StringProperty adresseProperty() { return adresse; }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", dernierDiplome='" + getDernierDiplome() + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", adresse='" + getAdresse() + '\'' +
                '}';
    }
}

