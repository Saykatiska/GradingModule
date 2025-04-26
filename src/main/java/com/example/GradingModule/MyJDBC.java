package com.example.GradingModule;

import java.sql.*;

public class MyJDBC {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres";
        String user = "postgres.odyfrnuddvhbedvjfnhw";
        String password = "HelloWorld123!";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement countStatement = connection.createStatement();
             ResultSet countResult = countStatement.executeQuery("SELECT COUNT(*) FROM grades");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM grades")) {

            int rowCount = 0;
            if (countResult.next()) {
                rowCount = countResult.getInt(1);
            }
            System.out.println("Total number of records: " + rowCount);
            System.out.println();

            while (resultSet.next()) {
                String studentId = resultSet.getString("studid");
                String subjectCode = resultSet.getString("subjcode");
                String finalGrade = resultSet.getString("fingrade");
                String gradeStatus = resultSet.getString("gradestat");

                System.out.println("Student ID: " + studentId);
                System.out.println("Subject Code: " + subjectCode);
                System.out.println("Final Grade: " + finalGrade);
                System.out.println("Grade Status: " + gradeStatus);
                System.out.println();
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred:");
            e.printStackTrace();
        }
    }
}