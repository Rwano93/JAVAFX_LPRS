package com.gestionevenements.controllers;

import com.gestionevenements.models.Etudiant;
import com.gestionevenements.services.EtudiantService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

public class EtudiantController {

    @FXML
    private TableView<Etudiant> etudiantTableView;
    @FXML
    private TableColumn<Etudiant, String> nomColumn;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn;
    @FXML
    private TableColumn<Etudiant, String> emailColumn;
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

    private EtudiantService etudiantService;

    public EtudiantController() {
        this.etudiantService = new EtudiantService();
    }

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        updateEtudiantList();

        etudiantTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showEtudiantDetails(newSelection);
            }
        });
    }

    private void updateEtudiantList() {
        etudiantTableView.setItems(FXCollections.observableArrayList(etudiantService.getAllEtudiants()));
    }

    private void showEtudiantDetails(Etudiant etudiant) {
        nomField.setText(etudiant.getNom());
        prenomField.setText(etudiant.getPrenom());
        emailField.setText(etudiant.getEmail());
        telephoneField.setText(etudiant.getTelephone());
        adresseField.setText(etudiant.getAdresse());
        dernierDiplomeField.setText(etudiant.getDernierDiplome());
    }

    @FXML
    private void handleAjouterEtudiant() {
        Etudiant nouvelEtudiant = new Etudiant(
                0,
                nomField.getText(),
                prenomField.getText(),
                emailField.getText(),
                "",  // Le mot de passe sera généré par le service
                dernierDiplomeField.getText(),
                telephoneField.getText(),
                adresseField.getText()
        );

        boolean success = etudiantService.ajouterEtudiant(nouvelEtudiant);
        if (success) {
            updateEtudiantList();
            clearFields();
        } else {
            showAlert("Erreur", "Impossible d'ajouter l'étudiant.");
        }
    }

    @FXML
    private void handleModifierEtudiant() {
        Etudiant selectedEtudiant = etudiantTableView.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            selectedEtudiant.setNom(nomField.getText());
            selectedEtudiant.setPrenom(prenomField.getText());
            selectedEtudiant.setEmail(emailField.getText());
            selectedEtudiant.setTelephone(telephoneField.getText());
            selectedEtudiant.setAdresse(adresseField.getText());
            selectedEtudiant.setDernierDiplome(dernierDiplomeField.getText());

            boolean success = etudiantService.modifierEtudiant(selectedEtudiant);
            if (success) {
                updateEtudiantList();
                clearFields();
            } else {
                showAlert("Erreur", "Impossible de modifier l'étudiant.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un étudiant à modifier.");
        }
    }

    @FXML
    private void handleSupprimerEtudiant() {
        Etudiant selectedEtudiant = etudiantTableView.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            boolean confirmed = showConfirmation("Confirmation", "Êtes-vous sûr de vouloir supprimer cet étudiant ?");
            if (confirmed) {
                boolean success = etudiantService.supprimerEtudiant(selectedEtudiant.getId());
                if (success) {
                    updateEtudiantList();
                    clearFields();
                } else {
                    showAlert("Erreur", "Impossible de supprimer l'étudiant.");
                }
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un étudiant à supprimer.");
        }
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        telephoneField.clear();
        adresseField.clear();
        dernierDiplomeField.clear();
    }

    private void showAlert(String title, String content) {
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

