module com.example.gradingmodule {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.gradingmodule to javafx.fxml;
    exports com.example.gradingmodule;
}