module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jasperreports;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}