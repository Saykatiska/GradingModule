package com.example.GradingModule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GradingModuleController implements Initializable {

    @FXML
    private Label facultyName;

    @FXML
    private TableView<Subject> subjectsTable;

    @FXML
    private TableColumn<Subject, String> editBtnCol;

    @FXML
    private TableColumn<Subject, String> yearSecCol;

    @FXML
    private TableColumn<Subject, String> semCol;

    @FXML
    private TableColumn<Subject, String> subjCodeCol;

    @FXML
    private TableColumn<Subject, String> subjDescCol;

    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();

    // Database connection constants
    private static final String URL = "jdbc:postgresql://db.autqwzshfjaqbkxpiqxm.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "pupSISProject2025";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Verify that FXML injection worked
        if (subjectsTable == null) {
            System.err.println("Error: subjectsTable is null. Check FXML file for proper fx:id.");
            return;
        }

        // Initialize the columns
        yearSecCol.setCellValueFactory(new PropertyValueFactory<>("yearSection"));
        semCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        subjCodeCol.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
        subjDescCol.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));

        // Load data from database
        loadSubjectsData();

        // Set the items to the TableView
        subjectsTable.setItems(subjectsList);
    }

    private void loadSubjectsData() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM subjects")) {

            while (rs.next()) {
                subjectsList.add(new Subject(
                    rs.getString("year_section"),
                    rs.getString("semester"),
                    rs.getString("subject_code"),
                    rs.getString("subject_description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshTable() {
        subjectsList.clear();
        loadSubjectsData();
    }
}