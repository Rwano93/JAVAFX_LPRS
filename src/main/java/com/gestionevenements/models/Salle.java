package com.gestionevenements.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Salle {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty nom = new SimpleStringProperty(this, "nom");
    private final IntegerProperty capacite = new SimpleIntegerProperty(this, "capacite");

    public Salle(int id, String nom, int capacite) {
        this.id.set(id);
        this.nom.set(nom);
        this.capacite.set(capacite);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public int getCapacite() { return capacite.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setCapacite(int capacite) { this.capacite.set(capacite); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public IntegerProperty capaciteProperty() { return capacite; }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", capacite=" + getCapacite() +
                '}';
    }
}

