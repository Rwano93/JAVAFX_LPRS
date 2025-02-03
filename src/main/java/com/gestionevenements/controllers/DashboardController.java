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
                "Rendez-vous ajouté par Professeur X",
                "Dossier d'inscription validé pour Etudiant Y",
                "Stock de fournitures mis à jour"
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
        navigateTo("login-view.fxml");
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

    private void showInscriptionsView() {
        navigateTo("inscription-view.fxml");
    }

    private void showFournisseursView() {
        navigateTo("fournisseur-view.fxml");
    }


    private void showMyInscriptionFile() {
        // Implement the logic to show the student's own inscription file
        String userEmail = SessionManager.getInstance().getLoggedInUser();
        DossierInscription dossier = inscriptionService.getDossierInscriptionByEmail(userEmail);
        if (dossier != null) {
            showAlert(Alert.AlertType.INFORMATION, "Dossier d'inscription", dossier.toString());
        } else {
            showAlert(Alert.AlertType.WARNING, "Dossier non trouvé", "Aucun dossier d'inscription trouvé pour cet étudiant.");
        }
    }

    private void showEmploiDuTemps() {
        // Implement the logic to show the professor's schedule
        showAlert(Alert.AlertType.INFORMATION, "Emploi du temps", "Fonctionnalité à implémenter : Affichage de l'emploi du temps du professeur.");
    }

    private void showCommandeFournitures() {
        // Implement the logic to show the supply order form
        showAlert(Alert.AlertType.INFORMATION, "Commande de fournitures", "Fonctionnalité à implémenter : Formulaire de commande de fournitures.");
    }

    private void navigateToLogin() {
        navigateTo("login-view.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            String fxmlPath = "/com/gestionevenements/view/" + fxmlFile;
            URL fxmlLocation = getClass().getResource(fxmlPath);

            if (fxmlLocation == null) {
                throw new IOException("Fichier FXML introuvable : " + fxmlPath);
            }

            System.out.println("Chargement de la vue : " + fxmlFile);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller == null) {
                throw new NullPointerException("Le contrôleur pour " + fxmlFile + " est NULL.");
            }

            System.out.println("Contrôleur chargé avec succès pour " + fxmlFile);

            Stage stage = (Stage) userInfoLabel.getScene().getWindow();
            Scene scene = new Scene(root);

            String cssPath = "/com/gestionevenements/styles/global.css";
            URL cssLocation = getClass().getResource(cssPath);
            if (cssLocation != null) {
                scene.getStylesheets().add(cssLocation.toExternalForm());
                System.out.println("Styles CSS appliqués depuis : " + cssPath);
            } else {
                System.err.println("Fichier CSS introuvable : " + cssPath);
            }

            stage.setScene(scene);
            stage.show();
            System.out.println("Navigation réussie vers : " + fxmlFile);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Impossible de charger la vue demandée : " + fxmlFile + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur Inattendue", "Une erreur est survenue lors du chargement de la vue : " + fxmlFile + "\n" + e.getMessage());
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
        setupMenuItems();
        setupQuickActions();
    }
}

