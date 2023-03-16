package com.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String URLConnection;
    private final String username;
    private final String password;
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        URLConnection = "jdbc:mysql://localhost:3306/Tekone";/// the location and the path for the database
        username = "root";
        password = "";
    }


    public Connection getConnection() {
        try {
            System.out.println("Done");
            return DriverManager.getConnection(URLConnection, username, password);
        }
        catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
        return null;
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            System.out.println("Done");
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.err.println("Failed to close the database connection: " + e.getMessage());
        }
    }
}
