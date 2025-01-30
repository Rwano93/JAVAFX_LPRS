package com.gestionevenements.controllers;

import com.gestionevenements.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class DashboardController {

    @FXML private Label userInfoLabel;
    @FXML private Menu gestionMenu;
    @FXML private ToggleGroup menuToggleGroup;
    @FXML private GridPane summaryGrid;
    @FXML private ListView<String> recentActivitiesList;
    @FXML private FlowPane quickActionsPane;

    private String userRole;

    public DashboardController(Menu gestionMenu) {
        this.gestionMenu = gestionMenu;
    }

    public void initialize() {
        String userEmail = SessionManager.getInstance().getLoggedInUser();
        userRole = SessionManager.getInstance().getUserRole();
        userInfoLabel.setText("Connecté en tant que: " + userEmail + " (" + userRole + ")");

        if (gestionMenu != null) {
            setupMenuItems();
        }
        setupSummary();
        setupRecentActivities();
        setupQuickActions();
    }

    private void setupMenuItems() {
        List<MenuItem> menuItems = getMenuItemsForRole(userRole);
        if (gestionMenu != null) {
            gestionMenu.getItems().setAll(menuItems);
        }
    }

    private List<MenuItem> getMenuItemsForRole(String role) {
        return switch (role) {
            case "ETUDIANT" ->
                    Collections.singletonList(createMenuItem("Voir les rendez-vous", this::showRendezVousView));
            case "PROFESSEUR" ->
                    Collections.singletonList(createMenuItem("Gérer les rendez-vous", this::showRendezVousView));
            case "SECRETAIRE" -> Arrays.asList(
                    createMenuItem("Gérer les étudiants", this::showEtudiantsView),
                    createMenuItem("Gérer les rendez-vous", this::showRendezVousView)
            );
            case "GESTIONNAIRE_STOCK" -> Arrays.asList(
                    createMenuItem("Gérer les fournitures", this::showFournituresView),
                    createMenuItem("Gérer les stocks", this::showStocksView)
            );
            default -> Collections.emptyList();
        };
    }

    private MenuItem createMenuItem(String text, Runnable action) {
        MenuItem item = new MenuItem(text);
        item.setOnAction(e -> action.run());
        return item;
    }

    private void setupSummary() {
        addSummaryItem("Total étudiants", "150");
        addSummaryItem("Rendez-vous aujourd'hui", "10");
        addSummaryItem("Fournitures en stock", "500");
    }

    private void addSummaryItem(String label, String value) {
        int rowIndex = summaryGrid.getChildren().size() / 2;
        summaryGrid.add(new Label(label + ":"), 0, rowIndex);
        summaryGrid.add(new Label(value), 1, rowIndex);
    }

    private void setupRecentActivities() {
        ObservableList<String> activities = FXCollections.observableArrayList(
                "Nouvel étudiant inscrit: Jean Dupont",
                "Rendez-vous ajouté: Prof. Martin - 15:00",
                "Stock mis à jour: +50 stylos"
        );
        recentActivitiesList.setItems(activities);
    }

    private void setupQuickActions() {
        List<Button> actions = getQuickActionsForRole(userRole);
        quickActionsPane.getChildren().setAll(actions);
    }

    private List<Button> getQuickActionsForRole(String role) {
        return switch (role) {
            case "ETUDIANT" ->
                    Collections.singletonList(createActionButton("Voir mes rendez-vous", this::showRendezVousView));
            case "PROFESSEUR" ->
                    Collections.singletonList(createActionButton("Ajouter un rendez-vous", this::showRendezVousView));
            case "SECRETAIRE" -> Arrays.asList(
                    createActionButton("Ajouter un étudiant", this::showEtudiantsView),
                    createActionButton("Planifier un rendez-vous", this::showRendezVousView)
            );
            case "GESTIONNAIRE_STOCK" -> Arrays.asList(
                    createActionButton("Ajouter une fourniture", this::showFournituresView),
                    createActionButton("Mettre à jour le stock", this::showStocksView)
            );
            default -> Collections.emptyList();
        };
    }

    private Button createActionButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(e -> action.run());
        return button;
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().logout();
        navigateToLogin();
    }

    private void showEtudiantsView() {
        navigateTo("etudiant-view.fxml");
    }

    private void showRendezVousView() {
        navigateTo("rendez-vous-view.fxml");
    }

    private void showFournituresView() {
        navigateTo("fourniture-view.fxml");
    }

    private void showStocksView() {
        navigateTo("stock-view.fxml");
    }

    private void navigateToLogin() {
        navigateTo("login-view.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            String resourcePath = "/com/gestionevenements/" + fxmlFile;
            if (getClass().getResource(resourcePath) == null) {
                throw new IOException("Fichier FXML introuvable: " + resourcePath);
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resourcePath)));
            Stage stage = (Stage) userInfoLabel.getScene().getWindow();
            Scene scene = new Scene(root);

            String cssPath = "/styles/global.css";
            if (getClass().getResource(cssPath) != null) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Impossible de charger la page demandée.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initializeWithRole(String role) {
        userRole = role;
        initialize();
    }

    public ToggleGroup getMenuToggleGroup() {
        return menuToggleGroup;
    }

    public void setMenuToggleGroup(ToggleGroup menuToggleGroup) {
        this.menuToggleGroup = menuToggleGroup;
    }
}
