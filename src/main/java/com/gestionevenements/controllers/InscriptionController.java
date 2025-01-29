package com.gestionevenements.controllers;

import com.gestionevenements.models.Etudiant;
import com.gestionevenements.services.InscriptionService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;

public class InscriptionController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField dernierDiplomeField;
    @FXML
    private TextField filiereInteretField;
    @FXML
    private DatePicker dateInscriptionPicker;
    @FXML
    private TextArea motivationsArea;

    private InscriptionService inscriptionService;

    public InscriptionController() {
        this.inscriptionService = new InscriptionService();
    }

    @FXML
    private void handleInscription() {
        try {
            Etudiant etudiant = new Etudiant(
                    0, // l'id sera généré par la base de données
                    nomField.getText(),
                    prenomField.getText(),
                    emailField.getText(),
                    "", // le mot de passe sera généré et envoyé par email
                    dernierDiplomeField.getText(),
                    telephoneField.getText(),
                    adresseField.getText()
            );

            boolean inscriptionReussie = inscriptionService.inscrireEtudiant(
                    etudiant,
                    dateInscriptionPicker.getValue(),
                    filiereInteretField.getText(),
                    motivationsArea.getText()
            );

            if (inscriptionReussie) {
                showInfoAlert("Inscription réussie", "L'étudiant a été inscrit avec succès.");
                clearFields();
            } else {
                showErrorAlert("Erreur lors de l'inscription", "Impossible d'inscrire l'étudiant. Veuillez réessayer.");
            }
        } catch (Exception e) {
            showErrorAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        telephoneField.clear();
        adresseField.clear();
        dernierDiplomeField.clear();
        filiereInteretField.clear();
        dateInscriptionPicker.setValue(null);
        motivationsArea.clear();
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
