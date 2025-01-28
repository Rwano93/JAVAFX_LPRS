module com.example.javafx_lprs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com to javafx.fxml;
    exports com;
}