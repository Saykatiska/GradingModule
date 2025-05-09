package com.example.GradingModule;

import javafx.scene.control.Button;

public class Subject {
    private String yearSection;
    private String semester;
    private String subjectCode;
    private String subjectDescription;
    private Button editButton;


    public Subject(String yearSection, String semester, String subjectCode, String subjectDescription) {
        this.yearSection = yearSection;
        this.semester = semester;
        this.subjectCode = subjectCode;
        this.subjectDescription = subjectDescription;
    }

    // Getters
    public String getYearSection() { return yearSection; }
    public String getSemester() { return semester; }
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectDescription() { return subjectDescription; }

    // Setters
    public void setYearSection(String yearSection) { this.yearSection = yearSection; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public void setSubjectDescription(String subjectDescription) { this.subjectDescription = subjectDescription; }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

}