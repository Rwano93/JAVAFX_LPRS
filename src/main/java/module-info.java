module com.example.javafx_lprs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com to javafx.fxml;
    exports com;

    opens com.gestionevenements.utils to javafx.fxml;
    exports com.gestionevenements.utils;

    opens com.gestionevenements.controllers to javafx.fxml;
    exports com.gestionevenements.controllers;

    opens com.gestionevenements.models to javafx.fxml;
    exports com.gestionevenements.models;
    exports com.gestionevenements;
    opens com.gestionevenements to javafx.fxml;


}