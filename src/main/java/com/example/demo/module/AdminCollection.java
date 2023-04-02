package com.example.demo.module;

import com.example.demo.DatabaseConnection;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminCollection {

    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private List<Admin> adminList;

    public AdminCollection(List<Admin> adminList){
    this.adminList = adminList ;
    this.connection = DatabaseConnection.getInstance().getConnection();


    }
    public AdminCollection() {
        // Create a connection to the database
      this(new ArrayList<>());
      this.connection = DatabaseConnection.getInstance().getConnection();


    }

    public void addAdmin(Admin admin) {
        String query = "INSERT INTO admin (name, email, password, address, phone) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, admin.getName());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());
            preparedStatement.setString(4, admin.getAddress());
            preparedStatement.setString(5, admin.getPhone());
            int rowsInserted = preparedStatement.executeUpdate();

            // get the auto-generated admin_id value
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int adminId = generatedKeys.getInt(1);
                admin.setAdminId(adminId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public List<Admin> getAllAdmins() {


        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM admin");
            while (resultSet.next()) {
                Admin admin = new Admin(
                        resultSet.getInt("admin_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"));
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
    public List<Admin> searchAdmin(TextField keyword) {
        List<Admin> searchResults = new ArrayList<>();
        String query = "SELECT * FROM admin WHERE  admin_id LIKE ? OR name LIKE ? OR email LIKE ? OR address LIKE ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String value = "%" + keyword.getText() + "%";
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, value);
            preparedStatement.setString(3, value);
            preparedStatement.setString(4, value);



            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Admin admin  = new Admin(
                        resultSet.getInt("admin_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")

                );
                searchResults.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }


}
