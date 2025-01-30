package com.gestionevenements.controllers;

import com.gestionevenements.services.AuthenticationService;
import com.gestionevenements.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML private TextField emailFieldLogin;
    @FXML private PasswordField passwordFieldLogin;
    @FXML private TextField nomFieldRegister;
    @FXML private TextField prenomFieldRegister;
    @FXML private TextField emailFieldRegister;
    @FXML private PasswordField passwordFieldRegister;
    @FXML private ComboBox<String> roleComboBox;

    private final AuthenticationService authService;

    public LoginController() {
        this.authService = new AuthenticationService();
    }

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("ETUDIANT", "PROFESSEUR", "SECRETAIRE", "GESTIONNAIRE_STOCK");
    }

    @FXML
    private void handleLogin() {
        String email = emailFieldLogin.getText();
        String password = passwordFieldLogin.getText();

        try {
            if (authService.authenticate(email, password)) {
                String role = authService.getUserRole(email);
                SessionManager.getInstance().setLoggedInUser(email, role);
                navigateToDashboard(role);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Email ou mot de passe invalide. Veuillez réessayer.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Une erreur est survenue : " + e.getMessage());
        }
    }

    public void navigateToDashboard(String role) {
        try {
            String fxmlFile = "dashboard-view.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestionevenements/" + fxmlFile));
            Parent root = loader.load();

            // Get the controller and initialize it with the user's role
            DashboardController dashboardController = loader.getController();
            dashboardController.initializeWithRole(role);

            Stage stage = (Stage) emailFieldLogin.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/global.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement du tableau de bord.");
        }
    }

    @FXML
    private void handleRegister() {
        String nom = nomFieldRegister.getText();
        String prenom = prenomFieldRegister.getText();
        String email = emailFieldRegister.getText();
        String password = passwordFieldRegister.getText();
        String role = roleComboBox.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'inscription", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            if (authService.register(nom, prenom, email, password, role)) {
                showAlert(Alert.AlertType.INFORMATION, "Inscription réussie", "Vous pouvez maintenant vous connecter.");
                clearRegistrationFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur d'inscription", "L'inscription a échoué. Veuillez réessayer.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'inscription", "Une erreur est survenue : " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearRegistrationFields() {
        nomFieldRegister.clear();
        prenomFieldRegister.clear();
        emailFieldRegister.clear();
        passwordFieldRegister.clear();
        roleComboBox.getSelectionModel().clearSelection();
    }
}

