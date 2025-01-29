package com.gestionevenements.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    private static final String SALT = "gestionEvenements";

    public String hasherMotDePasse(String motDePasse) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT.getBytes());
            byte[] bytes = md.digest(motDePasse.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifierMotDePasse(String motDePasse, String motDePasseHash) {
        String nouveauHash = hasherMotDePasse(motDePasse);
        return nouveauHash != null && nouveauHash.equals(motDePasseHash);
    }
}

