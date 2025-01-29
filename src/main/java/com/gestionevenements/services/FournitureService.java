package com.gestionevenements.services;

import com.gestionevenements.database.FournitureDAO;
import com.gestionevenements.models.Fourniture;

import java.util.List;

public class FournitureService {

    private FournitureDAO fournitureDAO;

    public FournitureService() {
        this.fournitureDAO = new FournitureDAO();
    }

    public boolean ajouterFourniture(Fourniture fourniture) {
        return fournitureDAO.ajouter(fourniture) != -1;
    }

    public boolean supprimerFourniture(int id) {
        return fournitureDAO.supprimer(id);
    }

    public List<Fourniture> getAllFournitures() {
        return fournitureDAO.getTout();
    }

    public List<String> getAllFournisseurs() {
        // Cette méthode devrait retourner la liste de tous les fournisseurs
        // Pour simplifier, nous retournons une liste vide
        return List.of();
    }
}

