package com.gestionevenements.controllers;

import com.gestionevenements.models.Fourniture;
import com.gestionevenements.services.FournitureService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

public class FournitureController {

    @FXML
    private TextField libelleField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField quantiteField;
    @FXML
    private ComboBox<String> fournisseurComboBox;
    @FXML
    private TextField prixField;
    @FXML
    private ListView<Fourniture> fournitureListView;

    private FournitureService fournitureService;

    public FournitureController() {
        this.fournitureService = new FournitureService();
    }

    @FXML
    public void initialize() {
        // Initialiser la ComboBox des fournisseurs
        fournisseurComboBox.setItems(FXCollections.observableArrayList(fournitureService.getAllFournisseurs()));

        // Initialiser la ListView des fournitures
        updateFournitureList();
    }

    @FXML
    private void handleAjouterFourniture() {
        String libelle = libelleField.getText();
        String description = descriptionArea.getText();
        int quantite = Integer.parseInt(quantiteField.getText());
        String fournisseur = fournisseurComboBox.getValue();
        double prix = Double.parseDouble(prixField.getText());

        Fourniture fourniture = new Fourniture(0, libelle, description, quantite, fournisseur, prix);

        try {
            boolean success = fournitureService.ajouterFourniture(fourniture);
            if (success) {
                showInfoAlert("Succès", "La fourniture a été ajoutée avec succès.");
                clearFields();
                updateFournitureList();
            } else {
                showErrorAlert("Erreur", "Impossible d'ajouter la fourniture. Veuillez réessayer.");
            }
        } catch (Exception e) {
            showErrorAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    @FXML
    private void handleSupprimerFourniture() {
        Fourniture selectedFourniture = fournitureListView.getSelectionModel().getSelectedItem();
        if (selectedFourniture != null) {
            try {
                boolean success = fournitureService.supprimerFourniture(selectedFourniture.getId());
                if (success) {
                    showInfoAlert("Succès", "La fourniture a été supprimée avec succès.");
                    updateFournitureList();
                } else {
                    showErrorAlert("Erreur", "Impossible de supprimer la fourniture. Veuillez réessayer.");
                }
            } catch (Exception e) {
                showErrorAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
            }
        } else {
            showErrorAlert("Erreur", "Veuillez sélectionner une fourniture à supprimer.");
        }
    }

    private void updateFournitureList() {
        fournitureListView.setItems(FXCollections.observableArrayList(fournitureService.getAllFournitures()));
    }

    private void clearFields() {
        libelleField.clear();
        descriptionArea.clear();
        quantiteField.clear();

        fournisseurComboBox.setValue(null);
        prixField.clear();
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
