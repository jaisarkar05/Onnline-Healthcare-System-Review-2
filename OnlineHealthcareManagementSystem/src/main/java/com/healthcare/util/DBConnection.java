package com.healthcare.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database details
    private static final String URL =
            "jdbc:mysql://localhost:3306/healthcare_db";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "J@i$1000";

    // Method to get database connection
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD);

            System.out.println("Database connected successfully");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }

        return connection;
    }
}
