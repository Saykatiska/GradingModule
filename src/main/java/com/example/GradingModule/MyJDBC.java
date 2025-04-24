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
                resultSet.getString("studid");
                resultSet.getString("subjcode");
                resultSet.getString("fingrade");
                resultSet.getString("gradestat");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
