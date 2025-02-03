package com.gestionevenements.services;

import com.gestionevenements.database.EtudiantDAO;
import com.gestionevenements.database.DossierInscriptionDAO;
import com.gestionevenements.models.Etudiant;
import com.gestionevenements.models.DossierInscription;
import com.gestionevenements.utils.PasswordGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class InscriptionService {

    private EtudiantDAO etudiantDAO;
    private DossierInscriptionDAO dossierInscriptionDAO;
    private PasswordGenerator passwordGenerator;

    public InscriptionService() {
        this.etudiantDAO = new EtudiantDAO();
        this.dossierInscriptionDAO = new DossierInscriptionDAO();
        this.passwordGenerator = new PasswordGenerator();
    }

    public boolean inscrireEtudiant(Etudiant etudiant, String filiereInteret, String motivations) {
        // Générer un mot de passe temporaire
        String motDePasseTemp = passwordGenerator.genererMotDePasse();
        etudiant.setMotDePasse(motDePasseTemp);

        // Enregistrer l'étudiant dans la base de données
        int etudiantId = etudiantDAO.ajouter(etudiant);
        if (etudiantId == -1) {
            return false;
        }

        // Mettre à jour l'ID de l'étudiant
        etudiant.setId(etudiantId);

        // Créer le dossier d'inscription
        DossierInscription dossier = new DossierInscription(
                0,
                etudiant,
                LocalDateTime.now(),
                filiereInteret,
                motivations,
                "En attente"
        );

        // Enregistrer le dossier d'inscription
        return dossierInscriptionDAO.ajouter(dossier) != -1;
    }

    public boolean modifierDossierInscription(DossierInscription dossier) {
        return dossierInscriptionDAO.modifier(dossier);
    }

    public boolean supprimerDossierInscription(int id) {
        return dossierInscriptionDAO.supprimer(id);
    }

    public List<DossierInscription> getAllDossiersInscription() {
        return dossierInscriptionDAO.getTout();
    }

    public DossierInscription getDossierInscriptionById(int id) {
        return dossierInscriptionDAO.getById(id);
    }

    public DossierInscription getDossierInscriptionByEmail(String email) {
        Etudiant etudiant = etudiantDAO.trouverParEmail(email);
        if (etudiant != null) {
            return dossierInscriptionDAO.getByEtudiantId(etudiant.getId());
        }
        return null;
    }
}

