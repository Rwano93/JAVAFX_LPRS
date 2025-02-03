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
        if (!isSalleDisponible(rendezVous.getSalle(), rendezVous.getDate(), rendezVous.getHeure())) {
            return false;
        }
        return rendezVousDAO.ajouter(rendezVous) != -1;
    }

    public boolean supprimerRendezVous(int id) {
        return rendezVousDAO.supprimer(id);
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousDAO.getTout();
    }

    public List<RendezVous> getRendezVousByEtudiant(String email) {
        return rendezVousDAO.getByEtudiant(email);
    }

    public List<RendezVous> getRendezVousByProfesseur(String email) {
        return rendezVousDAO.getByProfesseur(email);
    }

    public List<String> getAllEtudiants() {
        return rendezVousDAO.getAllEtudiants();
    }

    public List<String> getAllProfesseurs() {
        return rendezVousDAO.getAllProfesseurs();
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
