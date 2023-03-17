package com.example.demo.module;

import com.example.demo.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersCollection  {
    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private List<Customer> customerList;

    public CustomersCollection(List<Customer> customerList){
        this.customerList = customerList ;
        this.connection = DatabaseConnection.getInstance().getConnection();


    }
    public CustomersCollection() {
        // Create a connection to the database
        this(new ArrayList<>());
        this.connection = DatabaseConnection.getInstance().getConnection();


    }



        public void addCustomer(Customer customer) {
            String query = "INSERT INTO customers (name, email, password, address, phone) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getEmail());
                preparedStatement.setString(3, customer.getPassword());
                preparedStatement.setString(4, customer.getAddress());
                preparedStatement.setString(5, customer.getPhone());
                int rowsInserted = preparedStatement.executeUpdate();

                // get the auto-generated customer_id value
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int customerId = generatedKeys.getInt(1);
                    customer.setCustomerId(customerId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<Customer> getAllCustomers() {
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                resultSet = connection.createStatement().executeQuery("SELECT * FROM customers");
                while (resultSet.next()) {
                    Customer customer = new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("address"), resultSet.getString("phone"));
                    customerList.add(customer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customerList;
        }

        public  void updateCustomer(Customer customer) {
            String query = "UPDATE customers SET name = ?, email = ?, password = ?, address = ?, phone = ? WHERE customer_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getEmail());
                preparedStatement.setString(3, customer.getPassword());
                preparedStatement.setString(4, customer.getAddress());
                preparedStatement.setString(5, customer.getPhone());
                preparedStatement.setInt(6, customer.getCustomerId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void deleteCustomer(int customer_id) {
            String query = "DELETE FROM customers WHERE customer_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, customer_id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




