package com.example.demo;

import com.example.demo.module.Orders;
import com.example.demo.module.OrdersCollection;
import com.example.demo.module.Products;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
@FXML
private TextField textInput;
    public UpdateOrdersController(Orders orders) {
        this.orders = orders;
    }

    public void initialize() {
        statusComboBox.setItems(FXCollections.observableArrayList("Pending","Shipped","Delivered"));


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
    public void setTable(TableView<Orders> table) {
        this.table = table;
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

    private void saveOrder(Orders orders) throws IOException {
        try {
            // Get a connection to the database
            Connection connection = DatabaseConnection.getInstance().getConnection();
            // Prepare a statement to update the order status in the database
            PreparedStatement stmt = connection.prepareStatement("UPDATE orders SET statues = ? WHERE order_id = ?");
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

    @FXML
    private void handelBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrdersList.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to load main.fxml");
            alert.setContentText("Please try again later.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handelUpdate(ActionEvent event) {
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Statues Updated");
        alert.setHeaderText(null);
        alert.setContentText("The Statues has been Updated to the database.");
        alert.showAndWait();
    }

}

