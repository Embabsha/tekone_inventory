package com.example.demo;

import com.example.demo.module.Products;
import com.example.demo.module.ProductsCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductsController {

@FXML
private Button add;
@FXML
private Button clear;
@FXML
private Button back;
    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private TextField brandField;
    @FXML private TextField yearField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;



    private ProductsCollection productsCollection = new ProductsCollection();
@FXML
private void handleAdd(ActionEvent event){
    String name = nameField.getText();
    String description = descriptionField.getText();
    String type = typeField.getText();
    String brand = brandField.getText();
    double year = Double.parseDouble(yearField.getText());
    int quantity = Integer.parseInt(quantityField.getText());
    double price = Integer.parseInt(priceField.getText());


    // Create a new Admin object
    Products products = new Products(0,  name ,  description,  type ,  brand,  year,  quantity,  price);

    // Add the new admin to the database
    productsCollection.addProducts(products);

    // Display a success message to the user
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Product Added");
    alert.setHeaderText(null);
    alert.setContentText("The Product has been added to the database.");
    alert.showAndWait();


    nameField.clear();
    descriptionField.clear();
    typeField.clear();
    brandField.clear();
    yearField.clear();
    quantityField.clear();
    priceField.clear();


}

@FXML
private  void handleClear(ActionEvent event){
    nameField.clear();
    descriptionField.clear();
    typeField.clear();
    brandField.clear();
    yearField.clear();
    quantityField.clear();
    priceField.clear();

}

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductsList.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
