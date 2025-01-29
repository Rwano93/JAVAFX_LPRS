package com.gestionevenements.models;

public class Etudiant extends Utilisateur {
    private String dernierDiplome;
    private String telephone;
    private String adresse;

    public Etudiant(int id, String nom, String prenom, String email, String motDePasse,
                    String dernierDiplome, String telephone, String adresse) {
        super(id, nom, prenom, email, motDePasse);
        this.dernierDiplome = dernierDiplome;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    // Getters
    public String getDernierDiplome() { return dernierDiplome; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }

    // Setters
    public void setDernierDiplome(String dernierDiplome) { this.dernierDiplome = dernierDiplome; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", dernierDiplome='" + dernierDiplome + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}

