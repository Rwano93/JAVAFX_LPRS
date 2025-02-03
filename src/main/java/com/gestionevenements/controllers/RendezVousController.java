package com.gestionevenements.controllers;

import com.gestionevenements.models.RendezVous;
import com.gestionevenements.models.Salle;
import com.gestionevenements.services.RendezVousService;
import com.gestionevenements.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class RendezVousController implements Initializable {

    @FXML private ComboBox<String> etudiantComboBox;
    @FXML private ComboBox<String> professeurComboBox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<LocalTime> heureComboBox;
    @FXML private ComboBox<Salle> salleComboBox;
    @FXML private TableView<RendezVous> rendezVousTableView;
    @FXML private TableColumn<RendezVous, String> etudiantColumn;
    @FXML private TableColumn<RendezVous, String> professeurColumn;
    @FXML private TableColumn<RendezVous, LocalDate> dateColumn;
    @FXML private TableColumn<RendezVous, LocalTime> heureColumn;
    @FXML private TableColumn<RendezVous, String> salleColumn;
    @FXML private Button ajouterButton;
    @FXML private Button modifierButton;
    @FXML private Button supprimerButton;

    private RendezVousService rendezVousService;
    private String userRole;

    public RendezVousController() {
        this.rendezVousService = new RendezVousService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRole = SessionManager.getInstance().getUserRole();
        setupComboBoxes();
        setupTableColumns();
        loadRendezVous();
        setupAccessControl();
    }

    private void setupComboBoxes() {
        etudiantComboBox.setItems(FXCollections.observableArrayList(rendezVousService.getAllEtudiants()));
        professeurComboBox.setItems(FXCollections.observableArrayList(rendezVousService.getAllProfesseurs()));
        salleComboBox.setItems(FXCollections.observableArrayList(rendezVousService.getAllSalles()));

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(18, 0);
        while (start.isBefore(end)) {
            heureComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    private void setupTableColumns() {
        etudiantColumn.setCellValueFactory(cellData -> cellData.getValue().etudiantProperty());
        professeurColumn.setCellValueFactory(cellData -> cellData.getValue().professeurProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        heureColumn.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
        salleColumn.setCellValueFactory(cellData -> cellData.getValue().getSalle().nomProperty());
    }

    private void loadRendezVous() {
        if ("ETUDIANT".equals(userRole)) {
            String userEmail = SessionManager.getInstance().getLoggedInUser();
            rendezVousTableView.setItems(FXCollections.observableArrayList(rendezVousService.getRendezVousByEtudiant(userEmail)));
        } else if ("PROFESSEUR".equals(userRole)) {
            String userEmail = SessionManager.getInstance().getLoggedInUser();
            rendezVousTableView.setItems(FXCollections.observableArrayList(rendezVousService.getRendezVousByProfesseur(userEmail)));
        } else {
            rendezVousTableView.setItems(FXCollections.observableArrayList(rendezVousService.getAllRendezVous()));
        }
    }

    private void setupAccessControl() {
        boolean canModify = "SECRETAIRE".equals(userRole) || "PROFESSEUR".equals(userRole);
        ajouterButton.setVisible(canModify);
        modifierButton.setVisible(canModify);
        supprimerButton.setVisible(canModify);

        if ("ETUDIANT".equals(userRole)) {
            etudiantComboBox.setDisable(true);
            String userEmail = SessionManager.getInstance().getLoggedInUser();
            etudiantComboBox.setValue(userEmail);
        }

        if ("PROFESSEUR".equals(userRole)) {
            professeurComboBox.setDisable(true);
            String userEmail = SessionManager.getInstance().getLoggedInUser();
            professeurComboBox.setValue(userEmail);
        }
    }

    @FXML
    private void handleAjouterRendezVous() {
        String etudiant = etudiantComboBox.getValue();
        String professeur = professeurComboBox.getValue();
        LocalDate date = datePicker.getValue();
        LocalTime heure = heureComboBox.getValue();
        Salle salle = salleComboBox.getValue();

        if (etudiant == null || professeur == null || date == null || heure == null || salle == null) {
            showAlert(Alert.AlertType.WARNING, "Champs incomplets", "Veuillez remplir tous les champs.");
            return;
        }

        RendezVous rendezVous = new RendezVous(0, etudiant, professeur, date, heure, salle);
        boolean success = rendezVousService.prendreRendezVous(rendezVous);

        if (success) {
            loadRendezVous();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le rendez-vous a été ajouté avec succès.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le rendez-vous.");
        }
    }

    @FXML
    private void handleModifierRendezVous() {
        RendezVous selectedRendezVous = rendezVousTableView.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            etudiantComboBox.setValue(selectedRendezVous.getEtudiant());
            professeurComboBox.setValue(selectedRendezVous.getProfesseur());
            datePicker.setValue(selectedRendezVous.getDate());
            heureComboBox.setValue(selectedRendezVous.getHeure());
            salleComboBox.setValue(selectedRendezVous.getSalle());
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un rendez-vous à modifier.");
        }
    }

    @FXML
    private void handleSupprimerRendezVous() {
        RendezVous selectedRendezVous = rendezVousTableView.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            boolean confirmed = showConfirmation("Confirmation de suppression", "Êtes-vous sûr de vouloir supprimer ce rendez-vous ?");
            if (confirmed) {
                boolean success = rendezVousService.supprimerRendezVous(selectedRendezVous.getId());
                if (success) {
                    loadRendezVous();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Le rendez-vous a été supprimé avec succès.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer le rendez-vous.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un rendez-vous à supprimer.");
        }
    }

    private void clearFields() {
        etudiantComboBox.setValue(null);
        professeurComboBox.setValue(null);
        datePicker.setValue(null);
        heureComboBox.setValue(null);
        salleComboBox.setValue(null);
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
