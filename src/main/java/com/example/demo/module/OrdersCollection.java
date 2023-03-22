package com.example.demo.module;

import com.example.demo.DatabaseConnection;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersCollection {

    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private List<Orders> ordersList;

    public OrdersCollection(List<Orders> ordersList){
        this.ordersList = ordersList ;
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public OrdersCollection() {
        // Create a connection to the database
        this(new ArrayList<>());
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addOrders(Orders orders) {
        String productQuery = "SELECT * FROM product WHERE product_id = ?";
        //String customerQuery = "SELECT * FROM customers WHERE customer_id = ?";
        String orderQuery = "INSERT INTO orders (product_Id, customer_Id, quantity, total, statues, admin_Id) VALUES (?, ?, ?, ?, ?, ?)";
        //String adminQuery = "SELECT * FROM admin WHERE admin_id";
       try {
            // Check if product exists in products table
            PreparedStatement productStatement = connection.prepareStatement(productQuery);
            productStatement.setInt(1, orders.getProductId());
            ResultSet productResult = productStatement.executeQuery();

            if (!productResult.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                return;
            }

            // Calculate order total
            double price = productResult.getDouble("price");
            int quantity = orders.getQuantity();
            double total = price * quantity;

            // Insert order into orders table
            PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
            orderStatement.setInt(1, orders.getProductId());
            orderStatement.setInt(2, orders.getCustomerId());
            orderStatement.setInt(3, quantity);
            orderStatement.setDouble(4, total);
            orderStatement.setString(5, orders.getStatues());
            orderStatement.setInt(6, orders.getAdminId());
            orderStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public List<Orders> getAllOrders() {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {
                Orders orders = new Orders(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("total"),
                        resultSet.getString("statues"),
                        resultSet.getInt(("admin_id")
                                )
                        );
                ordersList.add(orders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }
/**
    public void updateOrders(Orders orders) {
        String query = "UPDATE orders SET customer_id = ?, quantity = ?, total = ? WHERE order_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orders.getCustomerId());
            preparedStatement.setInt(3, orders.getQuantity());
            preparedStatement.setDouble(4, orders.getTotal());
            preparedStatement.setInt(5, orders.getOrderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */


    public void deleteOrders(int order_id) {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
