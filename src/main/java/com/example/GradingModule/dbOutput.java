package com.example.GradingModule;

import java.sql.*;

public class dbOutput {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres";
        String user = "postgres.odyfrnuddvhbedvjfnhw";
        String password = "HelloWorld123!";

        String sql = "SELECT g.*, s.* " +
                    "FROM grade g " +
                    "JOIN students s ON g.student_id = s.student_id";

        try (Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {


            int rowCount = 0;
            try (Statement countStatement = connection.createStatement();
                 ResultSet countResult = countStatement.executeQuery("SELECT COUNT(*) FROM grade")) {
                if (countResult.next()) {
                    rowCount = countResult.getInt(1);
                }
            }

            if (rowCount == 0) {
                System.out.println("No records found in the database.");
            } else {
                while (resultSet.next()) {
                    String studentId = resultSet.getString("student_id");
                    String subjectCode = resultSet.getString("subject_code");
                    float finalGrade = resultSet.getFloat("final_grade");
                    float midtermGrade = resultSet.getFloat("midterm_grade");
                    float aveGrade = resultSet.getFloat("ave_grade");
                    String gradeStatus = resultSet.getString("gradestat");


                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String middlename = resultSet.getString("middlename");
                    String studentName = firstname + " " + middlename + " " + lastname;


                    System.out.println("Student ID: " + studentId);
                    System.out.println("Student Name: " + studentName);
                    System.out.println("Subject Code: " + subjectCode);
                    System.out.println("Midterm Grade: " + midtermGrade);
                    System.out.println("Final Grade: " + finalGrade);
                    System.out.println("Average Grade: " + aveGrade);
                    System.out.println("Status: " + gradeStatus);
                    System.out.println("-----------------------");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred:");
            e.printStackTrace();
        }
    }
}