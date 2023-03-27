package com.example.demo;

import com.example.demo.module.AdminCollection;
import com.example.demo.module.Orders;
import com.example.demo.module.OrdersCollection;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Date;

import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.List;

public class AddOrdersController {
    @FXML private TextField customerIdField;
    @FXML private TextField productIdField;
    @FXML private TextField quantityField;
    @FXML private TextField totalField;
    @FXML private TextField statuesField;
    @FXML private TextField adminIdField;

@FXML
private Button add;
@FXML
private Button clear;
@FXML
private Button back;
private OrdersCollection ordersCollection = new OrdersCollection();
private Connection connection = this.connection = DatabaseConnection.getInstance().getConnection();
    ;
    private ResultSet resultSet;
    private Statement statement;
    private List<Orders> ordersList;


@FXML
private void handleAdd(ActionEvent event) throws IOException, SQLException {

    String statues = statuesField.getText();
    int customerId = Integer.parseInt(customerIdField.getText());
    int productId = Integer.parseInt(productIdField.getText());
    int quantity = Integer.parseInt(quantityField.getText());
    int adminId =Integer.parseInt(adminIdField.getText());
    Double total= Double.parseDouble(totalField.getText());
    Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

    if (!statues.equals("shipped") && !statues.equals("delivered") && !statues.equals("pending")) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Statues");
        alert.setHeaderText(null);
        alert.setContentText("The statues entered is not valid. Please enter either 'shipped', 'delivered', or 'pending'.");
        alert.showAndWait();
        return;
    }
// Check if product exists in products table
    String productQuery = "SELECT * FROM product WHERE product_id = ?";


    try {
        PreparedStatement productStatement = connection.prepareStatement(productQuery);
        productStatement.setInt(1, productId);
        ResultSet productResult = productStatement.executeQuery();

        if (!productResult.next()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The product ID entered does not exist.");
            alert.showAndWait();
            return;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return;
    }

// Check if customer exists in customers table
    String customerQuery = "SELECT * FROM customers WHERE customer_id = ?";

    try {
        PreparedStatement customerStatement = connection.prepareStatement(customerQuery);
        customerStatement.setInt(1, customerId);
       ResultSet customerResult = customerStatement.executeQuery();

        if (!customerResult.next()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Customer Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The customer ID entered does not exist.");
            alert.showAndWait();
            return;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return;
    }
    // Check if admin exists in admin table

    String adminQuery = "SELECT * FROM admin WHERE admin_id = ?";

    try {
        PreparedStatement adminStatement = connection.prepareStatement(adminQuery);
        adminStatement.setInt(1, adminId);
       ResultSet adminResult = adminStatement.executeQuery();

        if (!adminResult.next()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("admin Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The admin ID entered does not exist.");
            alert.showAndWait();
            return;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return;
    }

    Orders orders = new Orders(0, productId, customerId, quantity, total, statues, adminId, date);

    ordersCollection.addOrders(orders);

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Orders Added");
    alert.setHeaderText(null);
    alert.setContentText("The orders have been added to the database.");
    alert.showAndWait();

    customerIdField.clear();
    productIdField.clear();
    quantityField.clear();
    totalField.clear();
    adminIdField.clear();
    statuesField.clear();

}



@FXML
private  void handleClear(ActionEvent event){
    customerIdField.clear();
    productIdField.clear();
    quantityField.clear();
    totalField.clear();
    adminIdField.clear();
    statuesField.clear();

}

    @FXML
    private void handleBack(ActionEvent event) {
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


}
