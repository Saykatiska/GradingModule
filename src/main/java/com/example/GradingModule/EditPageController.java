package com.example.GradingModule;

import com.example.dbconnection.dbConnection2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditPageController {

    @FXML
    private Label subjDescLbl;

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

    private final ObservableList<Student> studentsList = FXCollections.observableArrayList();

    private String selectedSubjectCode; // New field to store the subject code
    private String selectedSubjectDesc;

    String url = dbConnection2.URL;
    String user = dbConnection2.USER;
    String password = dbConnection2.PASSWORD;

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

        // If a subject code was set before initialization, load it now
        if (selectedSubjectCode != null) {
            subjCodeCombBox.setText(selectedSubjectCode);
            loadStudentsBySubjectCode(selectedSubjectCode);
        }

    }

    private void loadStudentsBySubjectCode(String subjectCode) {
        // Create a temporary list to hold the students
        ObservableList<Student> tempList = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Optimize the query by selecting only needed columns and using an index hint if available
            String query = """
            SELECT ss."No.", ss.\"student_ID\", ss.\"student_name\", ss.\"subj_Code\", 
                   ss.\"finalGrade\", ss.\"gradeStat\"
            FROM students_subj ss
            WHERE ss.\"subj_Code\" = ?
            ORDER BY CAST(ss."No." AS INTEGER)""";

            try (PreparedStatement pstmt = conn.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY)) {

                pstmt.setFetchSize(500); // Set an appropriate fetch size
                pstmt.setString(1, subjectCode);

                try (ResultSet rs = pstmt.executeQuery()) {
                    // Pre-fetch column indices for better performance
                    final int noIndex = rs.findColumn("No.");
                    final int idIndex = rs.findColumn("student_ID");
                    final int nameIndex = rs.findColumn("student_name");
                    final int codeIndex = rs.findColumn("subj_Code");
                    final int gradeIndex = rs.findColumn("finalGrade");
                    final int statIndex = rs.findColumn("gradeStat");

                    while (rs.next()) {
                        Student student = new Student(
                                rs.getString(noIndex),
                                rs.getString(idIndex),
                                rs.getString(nameIndex),
                                rs.getString(codeIndex),
                                rs.getString(gradeIndex),
                                rs.getString(statIndex)
                        );
                        tempList.add(student);
                    }
                }
            }

            // Update UI on the JavaFX Application Thread
            javafx.application.Platform.runLater(() -> {
                studentsList.clear();
                studentsList.addAll(tempList);
                studentsTable.setItems(studentsList);
            });

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            // Show error on the JavaFX Application Thread
            javafx.application.Platform.runLater(() ->
                    showError("Failed to load student data: " + e.getMessage())
            );
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
            showError("Failed to load subject codes: " + e.getMessage());
        }
    }

    public void setSubjectCode(String subjectCode) {
        this.selectedSubjectCode = subjectCode;
        // Update the UI to reflect the selected subject code
        if (subjCodeCombBox != null) {
            subjCodeCombBox.setText(subjectCode);
            // Load the students for this subject code
            loadStudentsBySubjectCode(subjectCode);
        }
    }

    public void setSubjectDesc(String subjectDesc) {
        this.selectedSubjectDesc = subjectDesc;
        // Update the UI to reflect the selected subject code
        if (subjDescLbl != null) {
            subjDescLbl.setText(subjectDesc);
        }
    }

    private void showError(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}