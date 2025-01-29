package com.gestionevenements.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stock {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty nomProduit = new SimpleStringProperty(this, "nomProduit");
    private final IntegerProperty quantite = new SimpleIntegerProperty(this, "quantite");
    private final StringProperty emplacement = new SimpleStringProperty(this, "emplacement");

    public Stock(int id, String nomProduit, int quantite, String emplacement) {
        this.id.set(id);
        this.nomProduit.set(nomProduit);
        this.quantite.set(quantite);
        this.emplacement.set(emplacement);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getNomProduit() { return nomProduit.get(); }
    public int getQuantite() { return quantite.get(); }
    public String getEmplacement() { return emplacement.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setNomProduit(String nomProduit) { this.nomProduit.set(nomProduit); }
    public void setQuantite(int quantite) { this.quantite.set(quantite); }
    public void setEmplacement(String emplacement) { this.emplacement.set(emplacement); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProduitProperty() { return nomProduit; }
    public IntegerProperty quantiteProperty() {
        return quantite;
    }
}


