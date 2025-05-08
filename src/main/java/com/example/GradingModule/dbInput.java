package com.example.GradingModule;

import com.example.dbconnection.dbConnection;
import java.sql.*;
import java.util.*;

public class dbInput {
    private static final Scanner input = new Scanner(System.in);
    
    public static void insertGrade(String studentId, String subjectCode, float finalGrade, float midtermGrade, float aveGrade, String gradeStatus) {
        String sql = "INSERT INTO grade (student_id, subject_code, final_grade, midterm_grade, ave_grade, gradestat) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            pstmt.setString(2, subjectCode);
            pstmt.setFloat(3, finalGrade);
            pstmt.setFloat(4, midtermGrade);
            pstmt.setFloat(5, aveGrade);
            pstmt.setString(6, gradeStatus);


            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Grade record inserted successfully.");
            } else {
                System.out.println("Failed to insert grade record.");
            }

        } catch (SQLException e) {
            System.err.println("Error inserting grade record:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    try {
        while (true) {
            System.out.print("Enter student ID: ");
            String studentId = input.nextLine();
            
            System.out.print("Enter subject code: ");
            String subjectCode = input.nextLine();
            
            System.out.print("Enter Midterm grade: ");
            float midtermGrade;
            midtermGrade = Float.parseFloat(input.nextLine());
                 
            System.out.print("Enter Final grade: ");
            float finalGrade;
            finalGrade = Float.parseFloat(input.nextLine());
            
            float aveGrade = (midtermGrade + finalGrade) / 2.0f;

            if (aveGrade > 1.0f && aveGrade <= 1.25f) {
                aveGrade = 1.25f;
            } else if (aveGrade > 1.25f && aveGrade <= 1.5f) {
                aveGrade = 1.5f;
            } else if (aveGrade > 1.5f && aveGrade <= 1.75f) {
                aveGrade = 1.75f;
            } else if (aveGrade > 1.75f && aveGrade <= 2.0f) {
                aveGrade = 2.0f;
            } else if (aveGrade > 2.0f && aveGrade <= 2.25f) {
                aveGrade = 2.25f;
            } else if (aveGrade > 2.25f && aveGrade <= 2.5f) {
                aveGrade = 2.5f;
            } else if (aveGrade > 2.5f && aveGrade <= 2.75f) {
                aveGrade = 2.75f;
            } else if (aveGrade > 2.75f && aveGrade <= 3.0f) {
                aveGrade = 3.0f;
            } else if (aveGrade == 1.0f) {
                aveGrade = 1.0f;
            }
            
            String gradeStatus;
            if (aveGrade >= 3.0) {
                gradeStatus = "F";
            } else {
                gradeStatus = "P";
            }

            insertGrade(
                studentId,
                subjectCode,
                finalGrade,
                midtermGrade,
                aveGrade,
                gradeStatus
            );
            
            System.out.print("Do you want to insert more records? (yes/no): ");
            String choice = input.nextLine().trim().toLowerCase();
            if (!choice.equals("yes")) {
                break;
            }
        }
    } catch (Exception e) {
        System.err.println("An error occurred during input:");
        e.printStackTrace();
    }
}
}