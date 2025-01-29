CREATE DATABASE IF NOT EXISTS gestion_evenements;
USE gestion_evenements;

CREATE TABLE utilisateurs (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              nom VARCHAR(50) NOT NULL,
                              prenom VARCHAR(50) NOT NULL,
                              email VARCHAR(100) NOT NULL UNIQUE,
                              mot_de_passe VARCHAR(255) NOT NULL,
                              role ENUM('ETUDIANT', 'PROFESSEUR', 'SECRETAIRE', 'GESTIONNAIRE_STOCK') NOT NULL
);

CREATE TABLE etudiants (
                           id INT PRIMARY KEY,
                           dernier_diplome VARCHAR(100),
                           telephone VARCHAR(20),
                           adresse TEXT,
                           FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

CREATE TABLE professeurs (
                             id INT PRIMARY KEY,
                             specialite VARCHAR(100),
                             FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

CREATE TABLE secretaires (
                             id INT PRIMARY KEY,
                             bureau VARCHAR(50),
                             FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

CREATE TABLE gestionnaires_stock (
                                     id INT PRIMARY KEY,
                                     departement VARCHAR(100),
                                     FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

CREATE TABLE dossiers_inscription (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      etudiant_id INT,
                                      date_heure DATETIME,
                                      filiere_interet VARCHAR(100),
                                      motivations TEXT,
                                      statut ENUM('En attente', 'Accepté', 'Refusé') DEFAULT 'En attente',
                                      FOREIGN KEY (etudiant_id) REFERENCES etudiants(id) ON DELETE CASCADE
);

CREATE TABLE salles (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(50) NOT NULL,
                        capacite INT NOT NULL
);

CREATE TABLE rendez_vous (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             etudiant_id INT,
                             professeur_id INT,
                             date DATE,
                             heure TIME,
                             salle_id INT,
                             FOREIGN KEY (etudiant_id) REFERENCES etudiants(id) ON DELETE CASCADE,
                             FOREIGN KEY (professeur_id) REFERENCES professeurs(id) ON DELETE CASCADE,
                             FOREIGN KEY (salle_id) REFERENCES salles(id) ON DELETE CASCADE
);

CREATE TABLE fournisseurs (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              nom VARCHAR(100) NOT NULL,
                              adresse TEXT,
                              telephone VARCHAR(20),
                              email VARCHAR(100)
);

CREATE TABLE fournitures (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             libelle VARCHAR(100) NOT NULL,
                             description TEXT,
                             quantite INT NOT NULL,
                             fournisseur_id INT,
                             prix DECIMAL(10, 2) NOT NULL,
                             FOREIGN KEY (fournisseur_id) REFERENCES fournisseurs(id) ON DELETE SET NULL
);

CREATE TABLE stocks (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nom_produit VARCHAR(100) NOT NULL,
                        quantite INT NOT NULL,
                        emplacement VARCHAR(100)
);

CREATE INDEX idx_etudiant_email ON utilisateurs(email) WHERE role = 'ETUDIANT';
CREATE INDEX idx_professeur_email ON utilisateurs(email) WHERE role = 'PROFESSEUR';
CREATE INDEX idx_rendez_vous_date ON rendez_vous(date);
CREATE INDEX idx_fourniture_libelle ON fournitures(libelle);
CREATE INDEX idx_stock_nom_produit ON stocks(nom_produit);