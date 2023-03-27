package com.example.demo.module;

import com.example.demo.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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
        String orderQuery = "INSERT INTO orders (product_Id, customer_Id, quantity, total,  statues, admin_Id, date) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

            // Check if the quantity ordered is less than or equal to the available quantity in the database
            int availableQuantity = productResult.getInt("quantity");
            int orderedQuantity = orders.getQuantity();

            if (orderedQuantity > availableQuantity) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Insufficient quantity available");
                alert.showAndWait();
                return;
            }

            // Calculate order total
            double price = productResult.getDouble("price");
            double total = price * orderedQuantity;

            // Update the quantity of the product in the database
            int updatedQuantity = availableQuantity - orderedQuantity;
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE product SET quantity = ? WHERE product_id = ?");
            updateStatement.setInt(1, updatedQuantity);
            updateStatement.setInt(2, orders.getProductId());
            updateStatement.executeUpdate();

            // Insert order into orders table
            PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
            orderStatement.setInt(1, orders.getProductId());
            orderStatement.setInt(2, orders.getCustomerId());
            orderStatement.setInt(3, orderedQuantity);
            orderStatement.setDouble(4, total);
            orderStatement.setString(5, orders.getStatues());
            orderStatement.setInt(6, orders.getAdminId());
            orderStatement.setDate(7, java.sql.Date.valueOf(java.time.LocalDate.now())); // set current date

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
                        resultSet.getInt("admin_id"),
                        resultSet.getDate("date")
                        );
                ordersList.add(orders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }



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

    public List<Orders> filterOrdersByStatus(String statues) {
        List<Orders> filteredOrders = new ArrayList<>();
        try {
            String query = "SELECT * FROM orders WHERE statues=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, statues);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Orders orders = new Orders (
                        resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("total"),
                        resultSet.getString("statues"),
                        resultSet.getInt("admin_id"),
                        resultSet.getDate("date")
                );
                filteredOrders.add(orders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredOrders;
    }
    public List<Orders> searchOrders(TextField keyword) {
        List<Orders> searchResults = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE  order_id LIKE ? OR  product_id LIKE ? OR   customer_id LIKE ? OR total LIKE ? OR statues LIKE ? OR admin_id LIKE ?   ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String value = "%" + keyword.getText() + "%";
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, value);
            preparedStatement.setString(3, value);
            preparedStatement.setString(4, value);
            preparedStatement.setString(5, value);
            preparedStatement.setString(6, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Orders orders = new Orders(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("total"),
                        resultSet.getString("statues"),
                        resultSet.getInt("admin_id"),
                        resultSet.getDate("date")

                );
                searchResults.add(orders);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }


}
