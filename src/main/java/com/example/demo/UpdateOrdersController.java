package com.example.demo;

import com.example.demo.module.Orders;
import com.example.demo.module.OrdersCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateOrdersController {

    @FXML
    private Button update;

    @FXML
    private Button back;

    private OrdersCollection ordersCollection;
    private Orders orders;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Label orderIdLabel;

    @FXML
    private TableView<Orders> table;

    public UpdateOrdersController(Orders orders) {
        this.orders = orders;
    }

    public void initialize() {
        statusComboBox.getItems().addAll("Pending", "Shipped", "Delivered");

        statusComboBox.setOnAction(event -> {
            // Get the selected order from your data model
            Orders selectedOrder = getSelectedOrder();
            if (selectedOrder != null) {
                // Update the status of the order
                selectedOrder.setStatues(statusComboBox.getValue());
                // Save the updated order to the database
                try {
                    saveOrder(selectedOrder);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Orders getSelectedOrder() {
        // Get the selected order from the table view
        Orders selectedOrder = table.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            int orderId = selectedOrder.getOrderId();
            orderIdLabel.setText(String.valueOf(orderId));
            return selectedOrder;
        }
        // If no order is selected, return null
        return null;
    }

    private void saveOrder(Orders order) throws IOException {
        try {
            // Get a connection to the database
            Connection connection = DatabaseConnection.getInstance().getConnection();
            // Prepare a statement to update the order status in the database
            PreparedStatement stmt = connection.prepareStatement("UPDATE orders SET status = ? WHERE order_id = ?");
            stmt.setString(1, order.getStatues());
            stmt.setInt(2, order.getOrderId());

            // Execute the update statement
            stmt.executeUpdate();

            // Close the statement and the connection
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrdersList.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        try {
            // Get a connection to the database
            Connection connection = DatabaseConnection.getInstance().getConnection();
            // Prepare a statement to update the order status in the database
            PreparedStatement stmt = connection.prepareStatement("UPDATE orders SET status = ? WHERE order_id = ?");
            stmt.setString(1, orders.getStatues());
            stmt.setInt(2, orders.getOrderId());

            // Execute the update statement
            stmt.executeUpdate();

            // Close the statement and the connection
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}

