package com.example.GradingModule;

import java.sql.*;

public class DBconnectionTrial {
    public static void main(String[] args) {
        String USER = "postgres";
        String URL = "jdbc:postgresql://db.autqwzshfjaqbkxpiqxm.supabase.co:5432/postgres";
        String PASSWORD = "pupSISProject2025";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement countStatement = connection.createStatement();
             ResultSet countResult = countStatement.executeQuery("SELECT COUNT(*) FROM students");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students")) {

            int rowCount = 0;
            if (countResult.next()) {
                rowCount = countResult.getInt(1);
            }
            System.out.println("Total number of records: " + rowCount);
            System.out.println();

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String program = resultSet.getString("program");

                System.out.println("Student ID: " + studentId);
                System.out.println("Student Name: " + firstName + " " + lastName);
                System.out.println("Program: " + program);
                System.out.println();
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred:");
            e.printStackTrace();
        }
    }
}