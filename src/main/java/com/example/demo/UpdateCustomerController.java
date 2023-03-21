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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerController {
    @FXML
    private Button update;
    @FXML
    private Button clear;
    @FXML
    private Button back;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    private Customer customer;
    @FXML private TableView<Customer> table;

    private CustomersCollection customersCollection;

    public UpdateCustomerController(Customer customer){
        this.customer = customer;

    }
    public void  initialize(){
        customersCollection = new CustomersCollection();
        // Show the current values in the  fields
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        passwordField.setText(customer.getPassword());
        addressField.setText(customer.getAddress());
        phoneField.setText(customer.getPhone());


    }
    public void setCustomer(Customer customer) {
        // Set the customer instance variable to the passed-in customer object
        this.customer = customer;

        // Set the text fields to the customer's current information
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        passwordField.setText(customer.getPassword());
        addressField.setText(customer.getAddress());
        phoneField.setText(customer.getPhone());
    }
    public void handleUpdate() {

        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        // create a new customer object with the updated data
        Customer updatedCustomer = new Customer(customer.getCustomerId(), name, email, password, address, phone);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Updated");
        alert.setHeaderText(null);
        alert.setContentText("The Customer has been Updated to the database.");
        alert.showAndWait();
        // update the customer data in the database
        CustomersCollection customersCollection = new CustomersCollection();
        customersCollection.updateCustomer(updatedCustomer.getCustomer());
        // update the customer data in the table view
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        table.getItems().set(selectedIndex, updatedCustomer);


    }


    @FXML
    private  void handleClear(ActionEvent event){
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
