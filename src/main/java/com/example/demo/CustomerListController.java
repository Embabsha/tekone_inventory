package com.example.demo;

import com.example.demo.module.Admin;
import com.example.demo.module.AdminCollection;
import com.example.demo.module.Customer;
import com.example.demo.module.CustomersCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerListController {
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button Back;
    @FXML
    private TableView<Customer> table;
    @FXML
    private TableColumn<Customer, Integer> colCustomerId;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer, String> colEmail;
    @FXML
    private TableColumn<Customer, String> colPassword;
    @FXML
    private TableColumn<Customer, String> colAddress;
    @FXML
    private TableColumn<Customer, String> colPhone;
    @FXML private TextField searchField;
    public void initialize() {
        load();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handelSearch(ActionEvent event) throws IOException{
        CustomersCollection customersCollection = new CustomersCollection();
        ArrayList<Customer> customers = new ArrayList<>(customersCollection.searchCustomer(searchField));
        ObservableList<Customer> data = FXCollections.observableArrayList(customers);
        table.setItems(data);

    }

    @FXML
    private void handleAdd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleDelete(ActionEvent event) throws IOException {
        // Get the selected items from the table view
        ObservableList<Customer> selectedItems = table.getSelectionModel().getSelectedItems();

        // Iterate over the selected items and delete them from the database
        for (Customer customer : selectedItems) {
            String query = "DELETE FROM customer WHERE customer_id = ?";
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, customer.getCustomerId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Refresh the table view
        table.getItems().removeAll(selectedItems);
        table.refresh();
    }

    @FXML
    private void handleUpdate(ActionEvent event) throws IOException {
        System.out.println(table.getSelectionModel().getSelectedItem());
        final UpdateCustomerController updateCustomerController = new UpdateCustomerController(table.getSelectionModel().getSelectedItem());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
        fxmlLoader.setController(updateCustomerController);
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void load() {
        colCustomerId.setCellValueFactory(cell -> cell.getValue().getCustomerIdProperty().asObject());
        colName.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        colEmail.setCellValueFactory(cell -> cell.getValue().getEmailProperty());
        colPassword.setCellValueFactory(cell -> cell.getValue().getPasswordProperty());
        colAddress.setCellValueFactory(cell -> cell.getValue().getAddressProperty());
        colPhone.setCellValueFactory(cell -> cell.getValue().getPhoneProperty());

        CustomersCollection customersCollection = new CustomersCollection();
        ArrayList<Customer> customers = new ArrayList<>(customersCollection.getAllCustomers());
        ObservableList<Customer> data = FXCollections.observableArrayList(customers);

        table.setItems(data);

    }
}
