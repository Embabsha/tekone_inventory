package com.example.demo;

import com.example.demo.module.Admin;
import com.example.demo.module.AdminCollection;
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

public class UpdateAdminController  {
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
    private Admin admin;
    @FXML private TableView<Admin> table;

    private AdminCollection adminCollection;

    public UpdateAdminController(Admin admin){
        this.admin = admin;

    }
    public void  initialize(){
        adminCollection = new AdminCollection();
        // Show the current values in the text fields
        nameField.setText(admin.getName());
        emailField.setText(admin.getEmail());
        passwordField.setText(admin.getPassword());
        addressField.setText(admin.getAddress());
        phoneField.setText(admin.getPhone());


    }
    public void setAdmin(Admin admin) {
        // Set the admin instance variable to the passed-in admin object
        this.admin = admin;

        // Set the text fields to the admin's current information
        nameField.setText(admin.getName());
        emailField.setText(admin.getEmail());
        passwordField.setText(admin.getPassword());
        addressField.setText(admin.getAddress());
        phoneField.setText(admin.getPhone());
    }
    @FXML
    public void handleUpdate() {

        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        // create a new admin object with the updated data
        Admin updatedAdmin = new Admin(admin.getAdminId(), name, email, password, address, phone);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin Updated");
        alert.setHeaderText(null);
        alert.setContentText("The admin has been Updated to the database.");
        alert.showAndWait();
        // update the admin data in the database
        adminCollection.updateAdmin(updatedAdmin.getAdmin());
        // update the admin data in the table view
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        table.getItems().set(selectedIndex, updatedAdmin);


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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminList.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


}


