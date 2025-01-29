module com.example.javafx_lprs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com to javafx.fxml;
    exports com;
    exports com;
    opens com to javafx.fxml;
    exports com.gestionevenements;
    opens com.gestionevenements to javafx.fxml;
}