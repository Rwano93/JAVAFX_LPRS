package com.gestionevenements.models;

public class Fourniture {
    private int id;
    private String libelle;
    private String description;
    private int quantite;
    private String fournisseur;
    private double prix;

    public Fourniture(int id, String libelle, String description, int quantite, String fournisseur, double prix) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.quantite = quantite;
        this.fournisseur = fournisseur;
        this.prix = prix;
    }

    // Getters
    public int getId() { return id; }
    public String getLibelle() { return libelle; }
    public String getDescription() { return description; }
    public int getQuantite() { return quantite; }
    public String getFournisseur() { return fournisseur; }
    public double getPrix() { return prix; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public void setDescription(String description) { this.description = description; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setFournisseur(String fournisseur) { this.fournisseur = fournisseur; }
    public void setPrix(double prix) { this.prix = prix; }

    @Override
    public String toString() {
        return "Fourniture{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                ", quantite=" + quantite +
                ", fournisseur='" + fournisseur + '\'' +
                ", prix=" + prix +
                '}';
    }
}

