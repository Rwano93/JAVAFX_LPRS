package com.gestionevenements.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NavigationUtil {

    public static void navigateTo(String fxmlFile, Stage stage) throws IOException {
        String fxmlPath = "/com/gestionevenements/view/" + fxmlFile;
        URL fxmlLocation = NavigationUtil.class.getResource(fxmlPath);

        if (fxmlLocation == null) {
            throw new IOException("Fichier FXML introuvable : " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void goToDashboard(Stage stage) throws IOException {
        navigateTo("dashboard-view.fxml", stage);
    }
}

