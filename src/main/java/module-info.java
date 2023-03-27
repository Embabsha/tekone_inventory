module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    exports com.example.demo.module;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}