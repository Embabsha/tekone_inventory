package com.example.demo;

import com.example.demo.module.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminListController  {
@FXML
private Button delete;
@FXML
private Button add;
@FXML
private Button update;
@FXML
private Button Back;
@FXML private TableView<Admin> table;
@FXML private TableColumn<Admin,Integer> colAdmin;
@FXML private TableColumn<Admin,String> colName;
@FXML private TableColumn<Admin,String> colEmail;
@FXML private TableColumn<Admin,String> colPassword;
@FXML private TableColumn<Admin,String> colAddress;
@FXML private TableColumn<Admin,String> colPhone;
@FXML private TextField searchField;



public void initialize()  {load();}

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
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
    public void handelSearch(ActionEvent event) throws IOException{
        AdminCollection adminCollection = new AdminCollection();
        ArrayList<Admin> admins = new ArrayList<>(adminCollection.searchAdmin(searchField));
        ObservableList<Admin> data = FXCollections.observableArrayList(admins);
        table.setItems(data);

    }


@FXML
private void handleAdd(ActionEvent event) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAdmin.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();

}
@FXML
private void handleDelete(ActionEvent event) throws IOException{
    // Get the selected items from the table view
    ObservableList<Admin> selectedItems = table.getSelectionModel().getSelectedItems();

    // Iterate over the selected items and delete them from the database
    for (Admin admin : selectedItems) {
        String query = "DELETE FROM admin WHERE admin_id = ?";
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, admin.getAdminId());
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
    private void handleUpdate() throws IOException {
        Admin selectedAdmin = table.getSelectionModel().getSelectedItem();
        if (selectedAdmin == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Customer selected");
            alert.setContentText("Please select a Customer from the table.");
            alert.showAndWait();
            return;
        }
        final UpdateAdminController updateadmincontroller = new UpdateAdminController(selectedAdmin);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateAdmin.fxml"));
        fxmlLoader.setController(updateadmincontroller);
        Parent root =(Parent) fxmlLoader.load();
        updateadmincontroller.setTable(table);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

@FXML
public void load() {
    colAdmin.setCellValueFactory(cell -> cell.getValue().getAdminIdProperty().asObject());
    colName.setCellValueFactory(cell -> cell.getValue().getNameProperty());
    colEmail.setCellValueFactory(cell -> cell.getValue().getEmailProperty());
    colPassword.setCellValueFactory(cell -> {
        String password = cell.getValue().getPassword();
        String asterisks = "*".repeat(password.length());
        return new SimpleStringProperty(asterisks);
    });
    colAddress.setCellValueFactory(cell -> cell.getValue().getAddressProperty());
    colPhone.setCellValueFactory(cell -> cell.getValue().getPhoneProperty());




    AdminCollection adminCollection = new AdminCollection();
    ArrayList<Admin> admins = new ArrayList<>(adminCollection.getAllAdmins());
    ObservableList<Admin> data = FXCollections.observableArrayList(admins);

    table.setItems(data);

    }
}
