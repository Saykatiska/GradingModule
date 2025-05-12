module com.example.gradingmodule {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires java.desktop;

    opens com.example.GradingModule to javafx.fxml;
    exports com.example.GradingModule;
}