package com.example.GradingModule;

public class Student {
    private String student_id;
    private String last_name;
    private String first_name;
    private String program;
    private String yearSection;
    
    // Constructor with all fields
    public Student(String student_id, String last_name, String first_name, String program, String yearSection) {
        this.student_id = student_id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.program = program;
        this.yearSection = yearSection;
    }
    
    // Getters and Setters
    public String getStudent_id() {
        return student_id;
    }
    
    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
    
    public String getLast_name() {
        return last_name;
    }
    
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public String getFirst_name() {
        return first_name;
    }
    
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    
    public String getProgram() {
        return program;
    }
    
    public void setProgram(String program) {
        this.program = program;
    }
    
    public String getYearSection() {
        return yearSection;
    }
    
    public void setYearSection(String yearSection) {
        this.yearSection = yearSection;
    }
    
    // Optional: Override toString() method for better object representation
    @Override
    public String toString() {
        return "Student{" +
                "student_id='" + student_id + '\'' +
                ", last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", program='" + program + '\'' +
                ", yearSection='" + yearSection + '\'' +
                '}';
    }
}