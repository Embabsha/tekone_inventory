package com.example.demo;

import com.example.demo.module.Customer;
import com.example.demo.module.CustomersCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddCustomersController  {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private Button add;
    @FXML private Button clear;
    @FXML private Button back;
    private CustomersCollection customerCollection = new CustomersCollection();


    @FXML
    private void handleAdd(ActionEvent event) {
        // Get input values from the user
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();

        // Create a new Customer object
        Customer customer = new Customer(0, name, email, password, address, phone);

        // Add the new customer to the database
        customerCollection.addCustomer(customer);

        // Display a success message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Added");
        alert.setHeaderText(null);
        alert.setContentText("The customer has been added to the database.");
        alert.showAndWait();

        nameField.clear();
        emailField.clear();
        passwordField.clear();
        addressField.clear();
        phoneField.clear();
    }


    @FXML
    private void handleClear(ActionEvent event){
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        addressField.clear();
        phoneField.clear();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerList.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
