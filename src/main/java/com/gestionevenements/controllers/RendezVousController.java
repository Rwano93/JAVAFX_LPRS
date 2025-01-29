package com.gestionevenements.controllers;

import com.gestionevenements.models.RendezVous;
import com.gestionevenements.models.Salle;
import com.gestionevenements.services.RendezVousService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVousController {

    @FXML
    private ComboBox<String> etudiantComboBox;
    @FXML
    private ComboBox<String> professeurComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<LocalTime> heureComboBox;
    @FXML
    private ComboBox<Salle> salleComboBox;

    private RendezVousService rendezVousService;

    public RendezVousController() {
        this.rendezVousService = new RendezVousService();
    }

    @FXML
    public void initialize() {
        // Initialiser les ComboBox avec les données
        etudiantComboBox.setItems(FXCollections.observableArrayList(rendezVousService.getAllEtudiants()));
        professeurComboBox.setItems(FXCollections.observableArrayList(rendezVousService.getAllProfesseurs()));
        salleComboBox.setItems(FXCollections.observableArrayList(rendezVousService.getAllSalles()));

        // Initialiser les heures disponibles (par exemple, de 9h à 17h par tranches de 30 minutes)
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        while (start.isBefore(end)) {
            heureComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }

        // Ajouter un listener pour mettre à jour les salles disponibles lorsque la date ou l'heure change
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateSallesDisponibles());
        heureComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateSallesDisponibles());
    }

    private void updateSallesDisponibles() {
        LocalDate date = datePicker.getValue();
        LocalTime heure = heureComboBox.getValue();
        if (date != null && heure != null) {
            salleComboBox.setItems(FXCollections.observableArrayList(rendezVousService.getSallesDisponibles(date, heure)));
        }
    }

    @FXML
    private void handlePrendreRendezVous() {
        String etudiant = etudiantComboBox.getValue();
        String professeur = professeurComboBox.getValue();
        LocalDate date = datePicker.getValue();
        LocalTime heure = heureComboBox.getValue();
        Salle salle = salleComboBox.getValue();

        if (etudiant == null || professeur == null || date == null || heure == null || salle == null) {
            showErrorAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        RendezVous rendezVous = new RendezVous(0, etudiant, professeur, date, heure, salle);

        try {
            boolean success = rendezVousService.prendreRendezVous(rendezVous);
            if (success) {
                showInfoAlert("Succès", "Le rendez-vous a été pris avec succès.");
                clearFields();
            } else {
                showErrorAlert("Erreur", "Impossible de prendre le rendez-vous. Veuillez réessayer.");
            }
        } catch (Exception e) {
            showErrorAlert("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private void clearFields() {
        etudiantComboBox.setValue(null);
        professeurComboBox.setValue(null);
        datePicker.setValue(null);
        heureComboBox.setValue(null);
        salleComboBox.setValue(null);
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
