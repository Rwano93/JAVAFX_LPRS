package com.gestionevenements.services;

import com.gestionevenements.database.EtudiantDAO;
import com.gestionevenements.models.Etudiant;

import java.util.List;

public class EtudiantService {

    private final EtudiantDAO etudiantDAO;

    public EtudiantService() {
        this.etudiantDAO = new EtudiantDAO();
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantDAO.getTout();
    }

    public boolean supprimerEtudiant(int id) {
        return etudiantDAO.supprimer(id);
    }

    public boolean ajouterEtudiant(Etudiant etudiant) {
        return etudiantDAO.ajouter(etudiant) != -1;
    }

    public Etudiant getEtudiantById(int id) {
        return etudiantDAO.getById(id);
    }

    public boolean modifierEtudiant(Etudiant selectedEtudiant) {
        return etudiantDAO.modifier(selectedEtudiant);
    }
}

