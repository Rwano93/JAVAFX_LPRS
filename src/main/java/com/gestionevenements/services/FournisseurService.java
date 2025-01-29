package com.gestionevenements.services;

import com.gestionevenements.database.FournisseurDAO;
import com.gestionevenements.models.Fournisseur;

import java.util.List;

public class FournisseurService {

    private FournisseurDAO fournisseurDAO;

    public FournisseurService() {
        this.fournisseurDAO = new FournisseurDAO();
    }

    public boolean ajouterFournisseur(Fournisseur fournisseur) {
        return fournisseurDAO.ajouter(fournisseur) != -1;
    }

    public boolean modifierFournisseur(Fournisseur fournisseur) {
        return fournisseurDAO.modifier(fournisseur);
    }

    public boolean supprimerFournisseur(int id) {
        return fournisseurDAO.supprimer(id);
    }

    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurDAO.getTout();
    }

    public Fournisseur getFournisseurById(int id) {
        return fournisseurDAO.getById(id);
    }
}

