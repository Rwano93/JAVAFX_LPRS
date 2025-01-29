package com.gestionevenements.services;

import com.gestionevenements.database.UtilisateurDAO;
import com.gestionevenements.models.Utilisateur;
import com.gestionevenements.utils.PasswordHasher;

public class AuthenticationService {

    private UtilisateurDAO utilisateurDAO;
    private PasswordHasher passwordHasher;

    public AuthenticationService() {
        this.utilisateurDAO = new UtilisateurDAO();
        this.passwordHasher = new PasswordHasher();
    }

    public boolean authenticate(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurDAO.trouverParEmail(email);
        if (utilisateur != null) {
            return passwordHasher.verifierMotDePasse(motDePasse, utilisateur.getMotDePasse());
        }
        return false;
    }

    public String getUserRole(String email) {
        return utilisateurDAO.getRoleUtilisateur(email);
    }
}

