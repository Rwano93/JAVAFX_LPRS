package com.gestionevenements.controllers;

import com.gestionevenements.models.Stock;
import com.gestionevenements.services.StockService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

public class StockController {

    @FXML
    private TextField nomProduitField;
    @FXML
    private TextField quantiteField;
    @FXML
    private TextField emplacementField;
    @FXML
    private TableView<Stock> stockTableView;
    @FXML
    private TableColumn<Stock, String> nomProduitColumn;
    @FXML
    private TableColumn<Stock, Number> quantiteColumn;
    @FXML
    private TableColumn<Stock, String> emplacementColumn;

    private StockService stockService;

    public StockController() {
        this.stockService = new StockService();
    }

    @FXML
    public void initialize() {
        nomProduitColumn.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
        quantiteColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
        emplacementColumn.setCellValueFactory(cellData -> cellData.getValue().emplacementProperty());

        updateStockList();
    }

    private void updateStockList() {
        stockTableView.setItems(FXCollections.observableArrayList(stockService.getAllStocks()));
    }

    @FXML
    private void handleAjouterStock() {
        try {
            String nomProduit = nomProduitField.getText();
            int quantite = Integer.parseInt(quantiteField.getText());
            String emplacement = emplacementField.getText();

            Stock stock = new Stock(0, nomProduit, quantite, emplacement);
            boolean success = stockService.ajouterStock(stock);
            if (success) {
                clearFields();
                updateStockList();
            } else {
                showAlert("Erreur", "Impossible d'ajouter le stock.");
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La quantité doit être un nombre entier.");
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
                    updateStockList();
                } else {
                    showAlert("Erreur", "Impossible de modifier le stock.");
                }
            } catch (NumberFormatException e) {
                showAlert("Erreur", "La quantité doit être un nombre entier.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un stock à modifier.");
        }
    }

    @FXML
    private void handleSupprimerStock() {
        Stock selectedStock = stockTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            boolean confirmed = showConfirmation("Confirmation", "Êtes-vous sûr de vouloir supprimer ce stock ?");
            if (confirmed) {
                boolean success = stockService.supprimerStock(selectedStock.getId());
                if (success) {
                    updateStockList();
                } else {
                    showAlert("Erreur", "Impossible de supprimer le stock.");
                }
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un stock à supprimer.");
        }
    }

    private void clearFields() {
        nomProduitField.clear();
        quantiteField.clear();
        emplacementField.clear();
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

