package com.gestionevenements.controllers;

import com.gestionevenements.services.AuthenticationService;
import com.gestionevenements.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private AuthenticationService authService;

    public LoginController() {
        this.authService = new AuthenticationService();
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (authService.authenticate(email, password)) {
            String role = authService.getUserRole(email);
            SessionManager.getInstance().setLoggedInUser(email, role);
            navigateToDashboard(role);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Email ou mot de passe invalide. Veuillez réessayer.");
            alert.showAndWait();
        }
    }

    private void navigateToDashboard(String role) {
        try {
            String fxmlFile = switch (role) {
                case "SECRETAIRE" -> "secretaire-view.fxml";
                case "PROFESSEUR" -> "professeur-view.fxml";
                case "GESTIONNAIRE_STOCK" -> "gestionnaire-stock-view.fxml";
                default -> "dashboard-view.fxml";
            };

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestionevenements/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Erreur lors du chargement du tableau de bord.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

