package com.gestionevenements.controllers;

import com.gestionevenements.models.DossierInscription;
import com.gestionevenements.models.Etudiant;
import com.gestionevenements.services.InscriptionService;
import com.gestionevenements.utils.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class InscriptionController implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private TextField dernierDiplomeField;
    @FXML private TextField filiereInteretField;
    @FXML private DatePicker dateInscriptionPicker;
    @FXML private TextArea motivationsArea;
    @FXML private TableView<DossierInscription> inscriptionTableView;
    @FXML private TableColumn<DossierInscription, String> nomColumn;
    @FXML private TableColumn<DossierInscription, String> prenomColumn;
    @FXML private TableColumn<DossierInscription, LocalDateTime> dateInscriptionColumn;
    @FXML private TableColumn<DossierInscription, String> filiereColumn;
    @FXML private TableColumn<DossierInscription, String> statutColumn;

    private InscriptionService inscriptionService;

    public InscriptionController() {
        this.inscriptionService = new InscriptionService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadInscriptions();
    }

    private void setupTableColumns() {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getEtudiant().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getEtudiant().prenomProperty());
        dateInscriptionColumn.setCellValueFactory(cellData -> cellData.getValue().dateHeureProperty());
        filiereColumn.setCellValueFactory(cellData -> cellData.getValue().filiereInteretProperty());
        statutColumn.setCellValueFactory(cellData -> cellData.getValue().statutProperty());
    }

    private void loadInscriptions() {
        inscriptionTableView.setItems(FXCollections.observableArrayList(inscriptionService.getAllDossiersInscription()));
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
                    filiereInteretField.getText(),
                    motivationsArea.getText()
            );

            if (inscriptionReussie) {
                showInfoAlert("Inscription réussie", "L'étudiant a été inscrit avec succès.");
                clearFields();
                loadInscriptions();
            } else {
                showErrorAlert("Erreur lors de l'inscription", "Impossible d'inscrire l'étudiant. Veuillez réessayer.");
            }
        } catch (Exception e) {
            showErrorAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    @FXML
    public void handleAjouterInscription(ActionEvent actionEvent) {
        // Cette méthode est déjà gérée par handleInscription()
        handleInscription();
    }

    @FXML
    public void handleModifierInscription(ActionEvent actionEvent) {
        DossierInscription selectedDossier = inscriptionTableView.getSelectionModel().getSelectedItem();
        if (selectedDossier != null) {
            // Remplir les champs avec les données du dossier sélectionné
            Etudiant etudiant = selectedDossier.getEtudiant();
            nomField.setText(etudiant.getNom());
            prenomField.setText(etudiant.getPrenom());
            emailField.setText(etudiant.getEmail());
            telephoneField.setText(etudiant.getTelephone());
            adresseField.setText(etudiant.getAdresse());
            dernierDiplomeField.setText(etudiant.getDernierDiplome());
            filiereInteretField.setText(selectedDossier.getFiliereInteret());
            dateInscriptionPicker.setValue(selectedDossier.getDateHeure().toLocalDate());
            motivationsArea.setText(selectedDossier.getMotivations());

            // Mettre à jour le dossier
            if (inscriptionService.modifierDossierInscription(selectedDossier)) {
                showInfoAlert("Modification réussie", "Le dossier d'inscription a été modifié avec succès.");
                loadInscriptions();
            } else {
                showErrorAlert("Erreur de modification", "Impossible de modifier le dossier d'inscription.");
            }
        } else {
            showErrorAlert("Sélection requise", "Veuillez sélectionner un dossier d'inscription à modifier.");
        }
    }

    @FXML
    public void handleSupprimerInscription(ActionEvent actionEvent) {
        DossierInscription selectedDossier = inscriptionTableView.getSelectionModel().getSelectedItem();
        if (selectedDossier != null) {
            boolean confirmed = showConfirmation("Confirmation de suppression", "Êtes-vous sûr de vouloir supprimer ce dossier d'inscription ?");
            if (confirmed) {
                if (inscriptionService.supprimerDossierInscription(selectedDossier.getId())) {
                    showInfoAlert("Suppression réussie", "Le dossier d'inscription a été supprimé avec succès.");
                    loadInscriptions();
                } else {
                    showErrorAlert("Erreur de suppression", "Impossible de supprimer le dossier d'inscription.");
                }
            }
        } else {
            showErrorAlert("Sélection requise", "Veuillez sélectionner un dossier d'inscription à supprimer.");
        }
    }

    @FXML
    public void handleVoirDetailsInscription(ActionEvent actionEvent) {
        DossierInscription selectedDossier = inscriptionTableView.getSelectionModel().getSelectedItem();
        if (selectedDossier != null) {
            showInfoAlert("Détails du dossier d'inscription", selectedDossier.toString());
        } else {
            showErrorAlert("Sélection requise", "Veuillez sélectionner un dossier d'inscription pour voir les détails.");
        }
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        try {
            NavigationUtil.goToDashboard((Stage) nomField.getScene().getWindow());
        } catch (IOException e) {
            showErrorAlert("Erreur de navigation", "Impossible de retourner au tableau de bord.");
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

    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}

