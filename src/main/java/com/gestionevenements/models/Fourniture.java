package com.gestionevenements.models;

import javafx.beans.property.*;

public class Fourniture {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty libelle = new SimpleStringProperty(this, "libelle");
    private final StringProperty description = new SimpleStringProperty(this, "description");
    private final IntegerProperty quantite = new SimpleIntegerProperty(this, "quantite");
    private final StringProperty fournisseur = new SimpleStringProperty(this, "fournisseur");
    private final DoubleProperty prix = new SimpleDoubleProperty(this, "prix");

    public Fourniture(int id, String libelle, String description, int quantite, String fournisseur, double prix) {
        this.id.set(id);
        this.libelle.set(libelle);
        this.description.set(description);
        this.quantite.set(quantite);
        this.fournisseur.set(fournisseur);
        this.prix.set(prix);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getLibelle() { return libelle.get(); }
    public String getDescription() { return description.get(); }
    public int getQuantite() { return quantite.get(); }
    public String getFournisseur() { return fournisseur.get(); }
    public double getPrix() { return prix.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setLibelle(String libelle) { this.libelle.set(libelle); }
    public void setDescription(String description) { this.description.set(description); }
    public void setQuantite(int quantite) { this.quantite.set(quantite); }
    public void setFournisseur(String fournisseur) { this.fournisseur.set(fournisseur); }
    public void setPrix(double prix) { this.prix.set(prix); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty libelleProperty() { return libelle; }
    public StringProperty descriptionProperty() { return description; }
    public IntegerProperty quantiteProperty() { return quantite; }
    public StringProperty fournisseurProperty() { return fournisseur; }
    public DoubleProperty prixProperty() { return prix; }

    @Override
    public String toString() {
        return "Fourniture{" +
                "id=" + getId() +
                ", libelle='" + getLibelle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", quantite=" + getQuantite() +
                ", fournisseur='" + getFournisseur() + '\'' +
                ", prix=" + getPrix() +
                '}';
    }
}

