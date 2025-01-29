package com.gestionevenements.models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVous {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty etudiant = new SimpleStringProperty(this, "etudiant");
    private final StringProperty professeur = new SimpleStringProperty(this, "professeur");
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "date");
    private final ObjectProperty<LocalTime> heure = new SimpleObjectProperty<>(this, "heure");
    private final ObjectProperty<Salle> salle = new SimpleObjectProperty<>(this, "salle");

    public RendezVous(int id, String etudiant, String professeur, LocalDate date, LocalTime heure, Salle salle) {
        this.id.set(id);
        this.etudiant.set(etudiant);
        this.professeur.set(professeur);
        this.date.set(date);
        this.heure.set(heure);
        this.salle.set(salle);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getEtudiant() { return etudiant.get(); }
    public String getProfesseur() { return professeur.get(); }
    public LocalDate getDate() { return date.get(); }
    public LocalTime getHeure() { return heure.get(); }
    public Salle getSalle() { return salle.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setEtudiant(String etudiant) { this.etudiant.set(etudiant); }
    public void setProfesseur(String professeur) { this.professeur.set(professeur); }
    public void setDate(LocalDate date) { this.date.set(date); }
    public void setHeure(LocalTime heure) { this.heure.set(heure); }
    public void setSalle(Salle salle) { this.salle.set(salle); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty etudiantProperty() { return etudiant; }
    public StringProperty professeurProperty() { return professeur; }
    public ObjectProperty<LocalDate> dateProperty() { return date; }
    public ObjectProperty<LocalTime> heureProperty() { return heure; }
    public ObjectProperty<Salle> salleProperty() { return salle; }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + getId() +
                ", etudiant='" + getEtudiant() + '\'' +
                ", professeur='" + getProfesseur() + '\'' +
                ", date=" + getDate() +
                ", heure=" + getHeure() +
                ", salle=" + getSalle() +
                '}';
    }
}

