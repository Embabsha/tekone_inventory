package com.example.demo;

import com.example.demo.module.Admin;
import com.example.demo.module.Products;
import com.example.demo.module.ProductsCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateProductsController {
    @FXML
    private Button update;
    @FXML
    private Button clear;
    @FXML
    private Button back;
    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private TextField brandField;
    @FXML private TextField yearField;
    //@FXML private Image ImageField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private ImageView imageField;



    private Products products;
    @FXML
    private TableView<Products> tableProducts;

    private ProductsCollection productCollection;

    public UpdateProductsController(Products products){
        this.products = products;
    }
    public void initialize(){
        productCollection = new ProductsCollection();

        nameField.setText(products.getName());
        descriptionField.setText(products.getDescription());
        typeField.setText(products.getType());
        brandField.setText(products.getBrand());
        yearField.setText(String.valueOf(products.getYear()));
        priceField.setText(String.valueOf(products.getPrice()));
        quantityField.setText(String.valueOf(products.getQuantity()));
        //Image image = new Image(products.getImage());
        //setImage(image);


    }
    public void setImage(Image image) {
        imageField.setImage(image);
    }


    public void setProducts(Products products){

        this.products = products;


        nameField.setText(products.getName());
        descriptionField.setText(products.getDescription());
        typeField.setText(products.getType());
        brandField.setText(products.getBrand());
        yearField.setText(String.valueOf(products.getYear()));
        priceField.setText(String.valueOf(products.getPrice()));
        quantityField.setText(String.valueOf(products.getQuantity()));

    }
    public void handleUpdate() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        String brand = brandField.getText();
        Double year = Double.valueOf(yearField.getText());
        Double price = Double.valueOf(priceField.getText());
        Integer quantity = Integer.valueOf(quantityField.getText());
        //Image image = imageField.getImage();
        //String imageUrl = image.getUrl();

        Products updateProducts = new Products(products.getProductId(), name, description, type, brand, year, quantity, price);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Products Updated");
        alert.setHeaderText(null);
        alert.setContentText("The Products has been Updated to the database.");
        alert.showAndWait();

        productCollection = new ProductsCollection();
        productCollection.updateProducts(updateProducts.getProductId(), updateProducts);
        // rest of the code
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductsList.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private  void handleClear(ActionEvent event) throws IOException{

        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        quantityField.clear();
        yearField.clear();
        typeField.clear();
        brandField.clear();
    }


    public void setTable(TableView<Products> tableProducts) {
        this.tableProducts = tableProducts;
    }


}
