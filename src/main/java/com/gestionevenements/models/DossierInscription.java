package com.gestionevenements.models;

import java.time.LocalDateTime;

public class DossierInscription {
    private int id;
    private Etudiant etudiant;
    private LocalDateTime dateHeure;
    private String filiereInteret;
    private String motivations;

    public DossierInscription(int id, Etudiant etudiant, LocalDateTime dateHeure, String filiereInteret, String motivations) {
        this.id = id;
        this.etudiant = etudiant;
        this.dateHeure = dateHeure;
        this.filiereInteret = filiereInteret;
        this.motivations = motivations;
    }

    // Getters
    public int getId() { return id; }
    public Etudiant getEtudiant() { return etudiant; }
    public LocalDateTime getDateHeure() { return dateHeure; }
    public String getFiliereInteret() { return filiereInteret; }
    public String getMotivations() { return motivations; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }
    public void setFiliereInteret(String filiereInteret) { this.filiereInteret = filiereInteret; }
    public void setMotivations(String motivations) { this.motivations = motivations; }

    @Override
    public String toString() {
        return "DossierInscription{" +
                "id=" + id +
                ", etudiant=" + etudiant +
                ", dateHeure=" + dateHeure +
                ", filiereInteret='" + filiereInteret + '\'' +
                ", motivations='" + motivations + '\'' +
                '}';
    }
}

