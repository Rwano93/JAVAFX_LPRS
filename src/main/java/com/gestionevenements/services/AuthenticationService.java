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
        Utilisateur utilisateur = utilisateurDAO.trouverParEmail(email);
        return utilisateur != null ? utilisateur.getRole() : null;
    }

    public boolean register(String nom, String prenom, String email, String motDePasse, String role) {
        if (utilisateurDAO.trouverParEmail(email) != null) {
            return false; // L'utilisateur existe déjà
        }
        String motDePasseHash = passwordHasher.hasherMotDePasse(motDePasse);
        Utilisateur nouvelUtilisateur = new Utilisateur(0, nom, prenom, email, motDePasseHash, role);
        return utilisateurDAO.ajouter(nouvelUtilisateur);
    }
}

