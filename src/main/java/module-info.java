module com.javafx_lprs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com to javafx.fxml;
    exports com;

    opens com.gestionevenements.controllers to javafx.fxml;
    exports com.gestionevenements.controllers;




}