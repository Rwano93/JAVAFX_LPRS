package com.gestionevenements.controllers;

import com.gestionevenements.models.Fournisseur;
import com.gestionevenements.services.FournisseurService;
import com.gestionevenements.utils.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.io.IOException;

public class FournisseurController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TableView<Fournisseur> fournisseurTableView;
    @FXML
    private TableColumn<Fournisseur, String> nomColumn;
    @FXML
    private TableColumn<Fournisseur, String> adresseColumn;
    @FXML
    private TableColumn<Fournisseur, String> telephoneColumn;
    @FXML
    private TableColumn<Fournisseur, String> emailColumn;

    private final FournisseurService fournisseurService;

    public FournisseurController() {
        this.fournisseurService = new FournisseurService();
    }

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        adresseColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
        telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        updateFournisseurList();
    }

    private void updateFournisseurList() {
        fournisseurTableView.setItems(FXCollections.observableArrayList(fournisseurService.getAllFournisseurs()));
    }

    @FXML
    private void handleAjouterFournisseur() {
        Fournisseur fournisseur = new Fournisseur(0, nomField.getText(), adresseField.getText(), telephoneField.getText(), emailField.getText());
        boolean success = fournisseurService.ajouterFournisseur(fournisseur);
        if (success) {
            clearFields();
            updateFournisseurList();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le fournisseur.");
        }
    }

    @FXML
    private void handleModifierFournisseur() {
        Fournisseur selectedFournisseur = fournisseurTableView.getSelectionModel().getSelectedItem();
        if (selectedFournisseur != null) {
            selectedFournisseur.setNom(nomField.getText());
            selectedFournisseur.setAdresse(adresseField.getText());
            selectedFournisseur.setTelephone(telephoneField.getText());
            selectedFournisseur.setEmail(emailField.getText());

            boolean success = fournisseurService.modifierFournisseur(selectedFournisseur);
            if (success) {
                clearFields();
                updateFournisseurList();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le fournisseur.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un fournisseur à modifier.");
        }
    }

    @FXML
    private void handleSupprimerFournisseur() {
        Fournisseur selectedFournisseur = fournisseurTableView.getSelectionModel().getSelectedItem();
        if (selectedFournisseur != null) {
            boolean confirmed = showConfirmation("Confirmation", "Êtes-vous sûr de vouloir supprimer ce fournisseur ?");
            if (confirmed) {
                boolean success = fournisseurService.supprimerFournisseur(selectedFournisseur.getId());
                if (success) {
                    updateFournisseurList();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le fournisseur.");
                }
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un fournisseur à supprimer.");
        }
    }

    private void clearFields() {
        nomField.clear();
        adresseField.clear();
        telephoneField.clear();
        emailField.clear();
    }

    private void showAlert(Alert.AlertType error, String title, String content) {
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
    @FXML
    private void handleBack() {
        try {
            NavigationUtil.goToDashboard((Stage) fournisseurTableView.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de revenir à la page précédente.");
        }
    }

}

