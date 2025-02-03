package com.gestionevenements.controllers;

import com.gestionevenements.models.Stock;
import com.gestionevenements.services.StockService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    @FXML private TextField nomProduitField;
    @FXML private TextField quantiteField;
    @FXML private TextField emplacementField;
    @FXML private TableView<Stock> stockTableView;
    @FXML private TableColumn<Stock, String> nomProduitColumn;
    @FXML private TableColumn<Stock, Number> quantiteColumn;
    @FXML private TableColumn<Stock, String> emplacementColumn;

    private StockService stockService;

    public StockController() {
        this.stockService = new StockService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadStocks();
    }

    private void setupTableColumns() {
        nomProduitColumn.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
        quantiteColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
        emplacementColumn.setCellValueFactory(cellData -> cellData.getValue().emplacementProperty());
    }

    private void loadStocks() {
        stockTableView.setItems(FXCollections.observableArrayList(stockService.getAllStocks()));
    }

    @FXML
    private void handleAjouterStock() {
        try {
            String nomProduit = nomProduitField.getText();
            int quantite = Integer.parseInt(quantiteField.getText());
            String emplacement = emplacementField.getText();

            if (nomProduit.isEmpty() || emplacement.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Champs incomplets", "Veuillez remplir tous les champs.");
                return;
            }

            Stock stock = new Stock(0, nomProduit, quantite, emplacement);
            boolean success = stockService.ajouterStock(stock);
            if (success) {
                clearFields();
                loadStocks();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le stock a été ajouté avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le stock.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité doit être un nombre entier.");
        }
    }

    @FXML
    private void handleModifierStock() {
        Stock selectedStock = stockTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            try {
                selectedStock.setNomProduit(nomProduitField.getText());
                selectedStock.setQuantite(Integer.parseInt(quantiteField.getText()));
                selectedStock.setEmplacement(emplacementField.getText());

                boolean success = stockService.modifierStock(selectedStock);
                if (success) {
                    clearFields();
                    loadStocks();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Le stock a été modifié avec succès.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le stock.");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité doit être un nombre entier.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un stock à modifier.");
        }
    }

    @FXML
    private void handleSupprimerStock() {
        Stock selectedStock = stockTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            boolean confirmed = showConfirmation("Confirmation de suppression", "Êtes-vous sûr de vouloir supprimer ce stock ?");
            if (confirmed) {
                boolean success = stockService.supprimerStock(selectedStock.getId());
                if (success) {
                    loadStocks();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Le stock a été supprimé avec succès.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le stock.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un stock à supprimer.");
        }
    }

    @FXML
    private void handleSelectionStock() {
        Stock selectedStock = stockTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            nomProduitField.setText(selectedStock.getNomProduit());
            quantiteField.setText(String.valueOf(selectedStock.getQuantite()));
            emplacementField.setText(selectedStock.getEmplacement());
        }
    }

    private void clearFields() {
        nomProduitField.clear();
        quantiteField.clear();
        emplacementField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
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
