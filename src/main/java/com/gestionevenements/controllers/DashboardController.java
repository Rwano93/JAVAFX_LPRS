package com.gestionevenements.controllers;

import com.gestionevenements.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label userInfoLabel;

    @FXML
    public void initialize() {
        String userEmail = SessionManager.getInstance().getLoggedInUser();
        String userRole = SessionManager.getInstance().getUserRole();
        userInfoLabel.setText("Connecté en tant que: " + userEmail + " (" + userRole + ")");
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().logout();
        navigateToLogin();
    }

    @FXML
    private void showEtudiantsView() {
        navigateTo("etudiant-view.fxml");
    }

    @FXML
    private void showRendezVousView() {
        navigateTo("rendez-vous-view.fxml");
    }

    @FXML
    private void showFournituresView() {
        navigateTo("fourniture-view.fxml");
    }

    @FXML
    private void showFournisseursView() {
        navigateTo("fournisseur-view.fxml");
    }

    @FXML
    private void showStocksView() {
        navigateTo("stock-view.fxml");
    }

    @FXML
    private void showReportsView() {
        // TODO: Implémenter la vue des rapports
    }

    private void navigateToLogin() {
        navigateTo("login-view.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gestionevenements/" + fxmlFile));
            Stage stage = (Stage) userInfoLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Afficher une alerte d'erreur
        }
    }
}

