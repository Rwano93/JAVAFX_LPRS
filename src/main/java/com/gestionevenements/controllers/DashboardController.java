package com.gestionevenements.controllers;

import com.gestionevenements.models.DossierInscription;
import com.gestionevenements.services.InscriptionService;
import com.gestionevenements.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.*;

public class DashboardController implements Initializable {

    @FXML private Label userInfoLabel;
    @FXML private Menu gestionMenu;
    @FXML private GridPane summaryGrid;
    @FXML private ListView<String> recentActivitiesList;
    @FXML private FlowPane quickActionsPane;
    @FXML private TableView<DossierInscription> inscriptionFilesTableView;
    @FXML private TableColumn<DossierInscription, String> nomColumn;
    @FXML private TableColumn<DossierInscription, String> prenomColumn;
    @FXML private TableColumn<DossierInscription, String> dateInscriptionColumn;
    @FXML private TableColumn<DossierInscription, String> statutColumn;

    private String userRole;
    private InscriptionService inscriptionService;

    public DashboardController() {
        this.inscriptionService = new InscriptionService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userEmail = SessionManager.getInstance().getLoggedInUser();
        userRole = SessionManager.getInstance().getUserRole();
        userInfoLabel.setText("Connecté en tant que: " + userEmail + " (" + userRole + ")");

        setupMenuItems();
        setupSummary();
        setupRecentActivities();
        setupQuickActions();
        setupInscriptionFilesTable();
    }

    private void setupMenuItems() {
        List<MenuItem> menuItems = getMenuItemsForRole(userRole);
        gestionMenu.getItems().setAll(menuItems);
    }

    private List<MenuItem> getMenuItemsForRole(String role) {
        return switch (role) {
            case "ETUDIANT" -> List.of(
                    createMenuItem("Voir les rendez-vous", this::showRendezVousView),
                    createMenuItem("Mon dossier d'inscription", this::showMyInscriptionFile)
            );
            case "PROFESSEUR" -> List.of(
                    createMenuItem("Gérer les rendez-vous", this::showRendezVousView)
            );
            case "SECRETAIRE" -> List.of(
                    createMenuItem("Gérer les étudiants", this::showEtudiantsView),
                    createMenuItem("Gérer les rendez-vous", this::showRendezVousView),
                    createMenuItem("Gérer les inscriptions", this::showInscriptionsView)
            );
            case "GESTIONNAIRE_STOCK" -> List.of(
                    createMenuItem("Gérer les fournitures", this::showFournituresView),
                    createMenuItem("Gérer les stocks", this::showStocksView),
                    createMenuItem("Gérer les fournisseurs", this::showFournisseursView)
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
        addSummaryItem("Dossiers d'inscription en attente", "25");
    }

    private void addSummaryItem(String label, String value) {
        int rowIndex = summaryGrid.getRowCount();
        summaryGrid.add(new Label(label + ":"), 0, rowIndex);
        summaryGrid.add(new Label(value), 1, rowIndex);
    }

    private void setupRecentActivities() {
        ObservableList<String> activities = FXCollections.observableArrayList(
                "Nouvel étudiant inscrit: Jean Dupont",
                "Rendez-vous ajouté: Prof. Martin - 15:00",
                "Stock mis à jour: +50 stylos",
                "Dossier d'inscription validé: Marie Curie"
        );
        recentActivitiesList.setItems(activities);
    }

    private void setupQuickActions() {
        List<Button> actions = getQuickActionsForRole(userRole);
        quickActionsPane.getChildren().setAll(actions);
    }

    private List<Button> getQuickActionsForRole(String role) {
        return switch (role) {
            case "ETUDIANT" -> List.of(
                    createActionButton("Voir mes rendez-vous", this::showRendezVousView),
                    createActionButton("Consulter mon dossier", this::showMyInscriptionFile)
            );
            case "PROFESSEUR" -> List.of(
                    createActionButton("Ajouter un rendez-vous", this::showRendezVousView),
                    createActionButton("Voir mon emploi du temps", this::showEmploiDuTemps)
            );
            case "SECRETAIRE" -> List.of(
                    createActionButton("Ajouter un étudiant", this::showEtudiantsView),
                    createActionButton("Planifier un rendez-vous", this::showRendezVousView),
                    createActionButton("Gérer les inscriptions", this::showInscriptionsView)
            );
            case "GESTIONNAIRE_STOCK" -> List.of(
                    createActionButton("Ajouter une fourniture", this::showFournituresView),
                    createActionButton("Mettre à jour le stock", this::showStocksView),
                    createActionButton("Commander des fournitures", this::showCommandeFournitures)
            );
            default -> Collections.emptyList();
        };
    }

    private Button createActionButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(e -> action.run());
        return button;
    }

    private void setupInscriptionFilesTable() {
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().getEtudiant().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getEtudiant().prenomProperty());
        dateInscriptionColumn.setCellValueFactory(cellData -> cellData.getValue().dateHeureProperty().asString());
        statutColumn.setCellValueFactory(cellData -> cellData.getValue().statutProperty());

        if ("ETUDIANT".equals(userRole)) {
            // Pour les étudiants, afficher uniquement leur propre dossier
            String userEmail = SessionManager.getInstance().getLoggedInUser();
            DossierInscription dossier = inscriptionService.getDossierInscriptionByEmail(userEmail);
            if (dossier != null) {
                inscriptionFilesTableView.setItems(FXCollections.observableArrayList(dossier));
            }
        } else {
            // Pour les autres rôles, afficher tous les dossiers
            List<DossierInscription> dossiers = inscriptionService.getAllDossiersInscription();
            inscriptionFilesTableView.setItems(FXCollections.observableArrayList(dossiers));
        }
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().logout();
        navigateToLogin();
    }

    private void showEtudiantsView() {
        navigateTo("/com/gestionevenements/view/etudiant-view.fxml");
    }

    private void showRendezVousView() {
        navigateTo("/com/gestionevenements/view/rendez-vous-view.fxml");
    }

    private void showFournituresView() {
        navigateTo("/com/gestionevenements/view/fourniture-view.fxml");
    }

    private void showStocksView() {
        navigateTo("/com/gestionevenements/view/stock-view.fxml");
    }

    private void showInscriptionsView() {
        navigateTo("/com/gestionevenements/view/inscription-view.fxml");
    }

    private void showFournisseursView() {
        navigateTo("/com/gestionevenements/view/fournisseur-view.fxml");
    }

    private void showMyInscriptionFile() {

    }

    private void showEmploiDuTemps() {

    }

    private void showCommandeFournitures() {

    }

    private void navigateToLogin() {
        navigateTo("/com/gestionevenements/view/login-view.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gestionevenements/view/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) userInfoLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());
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
}

