module com.example.gradingmodule {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.GradingModule to javafx.fxml;
    opens com.example.dbconnection;
    exports com.example.GradingModule;
    exports com.example.dbconnection;
}