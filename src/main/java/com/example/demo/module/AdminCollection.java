package com.example.demo.module;

import com.example.demo.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminCollection extends Application {

    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private List<Admin> adminList;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
    public AdminCollection(List<Admin> adminList){
    this.adminList = adminList ;

    }
    public AdminCollection() {
        // Create a connection to the database
      this(new ArrayList<>());

    }

    public void createAdmin(Admin admin) {
        String query = "INSERT INTO admin (name, email, password, address, phone) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2, admin.getName());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setString(5, admin.getAddress());
            preparedStatement.setString(6, admin.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public Admin readAdmin(int admin_id) {
        String query = "SELECT * FROM admin WHERE admin_id = ?";
        Admin admin = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, admin_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admin = new Admin(resultSet.getInt("admin_id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("address"), resultSet.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
    public List<Admin> getAllAdmins() {


        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM admin");
            while (resultSet.next()) {
                Admin admin = new Admin(resultSet.getInt("admin_id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("address"), resultSet.getString("phone"));
                adminList.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }


    public void updateAdmin(Admin admin) {
        String query = "UPDATE admin SET name = ?, email = ?, password = ?, address = ?, phone = ? WHERE admin_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());
            preparedStatement.setString(4, admin.getAddress());
            preparedStatement.setString(5, admin.getPhone());
            preparedStatement.setInt(6, admin.getAdminId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAdmin(int admin_id) {
        String query = "DELETE FROM admin WHERE admin_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, admin_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
