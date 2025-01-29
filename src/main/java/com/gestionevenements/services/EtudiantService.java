package com.gestionevenements.services;

import com.gestionevenements.database.EtudiantDAO;
import com.gestionevenements.models.Etudiant;

import java.util.List;

public class EtudiantService {

    private EtudiantDAO etudiantDAO;

    public EtudiantService() {
        this.etudiantDAO = new EtudiantDAO();
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantDAO.getTout();
    }

    public boolean supprimerEtudiant(int id) {
        return etudiantDAO.supprimer(id);
    }

    // Autres méthodes pour ajouter, modifier, etc.
}

