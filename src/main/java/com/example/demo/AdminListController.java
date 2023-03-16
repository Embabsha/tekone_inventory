package com.example.demo;

import com.example.demo.module.Admin;
import com.example.demo.module.AdminCollection;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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


public ObservableList<Admin> list = FXCollections.observableArrayList(

);

public void initialize()  {load();}

@FXML
private void handleBack(ActionEvent event ) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
}

@FXML
private void handleAdd(ActionEvent event) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAdmin.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

}
@FXML
private void handleDelete(ActionEvent event) throws IOException{


    }
@FXML
private void handleUpdate(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateAdmin.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

@FXML
public void load() {
    colAdmin.setCellValueFactory(cell -> cell.getValue().getAdminIdProperty().asObject());
    colName.setCellValueFactory(cell -> cell.getValue().getNameProperty());
    colEmail.setCellValueFactory(cell -> cell.getValue().getEmailProperty());
    colPassword.setCellValueFactory(cell -> cell.getValue().getPasswordProperty());
    colAddress.setCellValueFactory(cell -> cell.getValue().getAddressProperty());
    colPhone.setCellValueFactory(cell -> cell.getValue().getPhoneProperty());




    AdminCollection adminCollection = new AdminCollection();
    ArrayList<Admin> admins = new ArrayList<>(adminCollection.getAllAdmins());
    ObservableList<Admin> data = FXCollections.observableArrayList(admins);

    table.setItems(data);

    }
}
