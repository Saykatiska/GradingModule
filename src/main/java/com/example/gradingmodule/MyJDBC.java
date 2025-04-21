package com.example.gradingmodule;

import java.sql.*;

public class MyJDBC {
    public static void main(String[] args){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "pupProjectSIS2025"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student");

            while(resultSet.next()){
                System.out.println(resultSet.getString("lastName"));
                System.out.println(resultSet.getString("firstName"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
