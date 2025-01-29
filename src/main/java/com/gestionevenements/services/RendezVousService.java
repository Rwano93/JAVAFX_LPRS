package com.gestionevenements.services;

import com.gestionevenements.database.RendezVousDAO;
import com.gestionevenements.database.SalleDAO;
import com.gestionevenements.models.RendezVous;
import com.gestionevenements.models.Salle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RendezVousService {

    private RendezVousDAO rendezVousDAO;
    private SalleDAO salleDAO;

    public RendezVousService() {
        this.rendezVousDAO = new RendezVousDAO();
        this.salleDAO = new SalleDAO();
    }

    public boolean prendreRendezVous(RendezVous rendezVous) {
        // Vérifier si la salle est disponible
        if (!isSalleDisponible(rendezVous.getSalle(), rendezVous.getDate(), rendezVous.getHeure())) {
            return false;
        }

        // Enregistrer le rendez-vous
        return rendezVousDAO.ajouter(rendezVous) != -1;
    }

    public List<String> getAllEtudiants() {
        // Cette méthode devrait retourner la liste de tous les étudiants
        // Pour simplifier, nous retournons une liste vide
        return List.of();
    }

    public List<String> getAllProfesseurs() {
        // Cette méthode devrait retourner la liste de tous les professeurs
        // Pour simplifier, nous retournons une liste vide
        return List.of();
    }

    public List<Salle> getAllSalles() {
        return salleDAO.getTout();
    }

    public List<Salle> getSallesDisponibles(LocalDate date, LocalTime heure) {
        List<Salle> toutesLesSalles = salleDAO.getTout();
        toutesLesSalles.removeIf(salle -> !isSalleDisponible(salle, date, heure));
        return toutesLesSalles;
    }

    private boolean isSalleDisponible(Salle salle, LocalDate date, LocalTime heure) {
        return rendezVousDAO.getSalleDisponible(salle.getId(), date, heure);
    }
}

