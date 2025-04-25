package com.example.GradingModule;

import java.sql.*;

public class MyJDBC {
    public static void main(String[] args){

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres",
                    "postgres.odyfrnuddvhbedvjfnhw",
                    "HelloWorld123!"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM grades");

            while(resultSet.next()){
                String studentId = resultSet.getString("studid");
                String subjectCode = resultSet.getString("subjcode");
                String finalGrade = resultSet.getString("fingrade");
                String gradeStatus = resultSet.getString("gradestat");

                System.out.println("Student ID: " + studentId);
                System.out.println("Subject Code: " + subjectCode);
                System.out.println("Final Grade: " + finalGrade);
                System.out.println("Grade Status: " + gradeStatus);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
