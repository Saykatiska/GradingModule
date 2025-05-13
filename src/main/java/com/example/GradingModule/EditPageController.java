package com.example.GradingModule;

import com.example.dbconnection.dbConnection2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditPageController {

    @FXML
    private MenuButton subjCodeCombBox;

    @FXML
    private MenuButton yrSecCombBox;

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private TableColumn<Student, String> noStudCol;

    @FXML
    private TableColumn<Student, String> studIDCol;

    @FXML
    private TableColumn<Student, String> studNameCol;

    @FXML
    private TableColumn<Student, String> subjCodeCol;

    @FXML
    private TableColumn<Student, String> finGradeCol;

    @FXML
    private TableColumn<Student, String> gradeStatCol;

    private ObservableList<Student> studentsList = FXCollections.observableArrayList();

    dbConnection2 dbConn = new dbConnection2();
    String url = dbConn.URL;
    String user = dbConn.USER;
    String password = dbConn.PASSWORD;

    public void initialize(URL location, ResourceBundle resources) {
        if (studentsTable == null) {
            System.err.println("Error: studentsTable is null. Check FXML file for proper fx:id.");
            return;
        }

        // Initialize the columns with the correct property names
        noStudCol.setCellValueFactory(new PropertyValueFactory<>("studentNo"));
        studIDCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        subjCodeCol.setCellValueFactory(new PropertyValueFactory<>("subjCode"));
        finGradeCol.setCellValueFactory(new PropertyValueFactory<>("finalGrade"));
        gradeStatCol.setCellValueFactory(new PropertyValueFactory<>("gradeStatus"));

        // Populate the subject codes dropdown
        populateSubjectCodes();
        
        // Set default text for the MenuButton
        subjCodeCombBox.setText("Select Subject Code");
    }

    private void loadStudentsBySubjectCode(String subjectCode) {
        studentsList.clear();
        studentsTable.getItems().clear();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM students_subj WHERE \"subj_Code\" = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, subjectCode);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Student student = new Student(
                                rs.getString("No."),
                                rs.getString("student_ID"),
                                rs.getString("student_name"),
                                rs.getString("subj_Code"),
                                rs.getString("finalGrade"),
                                rs.getString("gradeStat")
                        );
                        studentsList.add(student);
                    }
                    studentsTable.setItems(studentsList);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            showError("Database Error", "Failed to load student data: " + e.getMessage());
        }
    }

    private void populateSubjectCodes() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Note the quoted column name "subject_code"
            String query = "SELECT DISTINCT \"subject_code\" FROM subjects2";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    String subjCode = rs.getString("subject_code");
                    javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem(subjCode);
                    item.setOnAction(event -> {
                        subjCodeCombBox.setText(subjCode);
                        loadStudentsBySubjectCode(subjCode);
                    });
                    subjCodeCombBox.getItems().add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading subject codes: " + e.getMessage());
            e.printStackTrace();
            showError("Database Error", "Failed to load subject codes: " + e.getMessage());
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}