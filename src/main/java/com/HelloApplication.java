package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file correctly
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/gestionevenements/view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Apply the CSS file correctly
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/gestionevenements/styles/global.css")).toExternalForm());

        // Set the title and scene
        stage.setTitle("Gestion d'Événements");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
