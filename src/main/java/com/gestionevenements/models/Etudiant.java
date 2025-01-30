package com.gestionevenements.models;

import javafx.beans.property.*;

public class Etudiant extends Utilisateur {
    private final StringProperty dernierDiplome;
    private final StringProperty telephone;
    private final StringProperty adresse;

    public Etudiant(int id, String nom, String prenom, String email, String motDePasse,
                    String dernierDiplome, String telephone, String adresse) {
        super(id, nom, prenom, email, motDePasse, "ETUDIANT");
        this.dernierDiplome = new SimpleStringProperty(dernierDiplome);
        this.telephone = new SimpleStringProperty(telephone);
        this.adresse = new SimpleStringProperty(adresse);
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
}

