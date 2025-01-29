package com.gestionevenements.models;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class DossierInscription {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<Etudiant> etudiant = new SimpleObjectProperty<>(this, "etudiant");
    private final ObjectProperty<LocalDateTime> dateHeure = new SimpleObjectProperty<>(this, "dateHeure");
    private final StringProperty filiereInteret = new SimpleStringProperty(this, "filiereInteret");
    private final StringProperty motivations = new SimpleStringProperty(this, "motivations");
    private final StringProperty statut = new SimpleStringProperty(this, "statut");

    public DossierInscription(int id, Etudiant etudiant, LocalDateTime dateHeure, String filiereInteret, String motivations, String statut) {
        this.id.set(id);
        this.etudiant.set(etudiant);
        this.dateHeure.set(dateHeure);
        this.filiereInteret.set(filiereInteret);
        this.motivations.set(motivations);
        this.statut.set(statut);
    }

    // Getters
    public int getId() { return id.get(); }
    public Etudiant getEtudiant() { return etudiant.get(); }
    public LocalDateTime getDateHeure() { return dateHeure.get(); }
    public String getFiliereInteret() { return filiereInteret.get(); }
    public String getMotivations() { return motivations.get(); }
    public String getStatut() { return statut.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setEtudiant(Etudiant etudiant) { this.etudiant.set(etudiant); }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure.set(dateHeure); }
    public void setFiliereInteret(String filiereInteret) { this.filiereInteret.set(filiereInteret); }
    public void setMotivations(String motivations) { this.motivations.set(motivations); }
    public void setStatut(String statut) { this.statut.set(statut); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public ObjectProperty<Etudiant> etudiantProperty() { return etudiant; }
    public ObjectProperty<LocalDateTime> dateHeureProperty() { return dateHeure; }
    public StringProperty filiereInteretProperty() { return filiereInteret; }
    public StringProperty motivationsProperty() { return motivations; }
    public StringProperty statutProperty() { return statut; }

    @Override
    public String toString() {
        return "DossierInscription{" +
                "id=" + getId() +
                ", etudiant=" + getEtudiant() +
                ", dateHeure=" + getDateHeure() +
                ", filiereInteret='" + getFiliereInteret() + '\'' +
                ", motivations='" + getMotivations() + '\'' +
                ", statut='" + getStatut() + '\'' +
                '}';
    }
}

