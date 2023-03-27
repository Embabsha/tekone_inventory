package com.example.demo;

import com.example.demo.module.Admin;
import com.example.demo.module.Products;
import com.example.demo.module.ProductsCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @FXML private Label imageLabel;



    private Products products;
    @FXML
    private TableView<Products> tableProducts;

    private ProductsCollection productCollection;

    List<String> lsFile;

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
        imageLabel.setText(String.valueOf(products.getImage()));



        lsFile = new ArrayList<>();
        lsFile.add(".jpeg");


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
        imageLabel.setText(String.valueOf(products.getImage()));

    }
    public void handleUpdate() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        String brand = brandField.getText();
        Double year = Double.valueOf(yearField.getText());
        Double price = Double.valueOf(priceField.getText());
        Integer quantity = Integer.valueOf(quantityField.getText());
        String image = imageLabel.getText();
        //String imageUrl = image.getUrl();

        if (imageLabel.getText() != null && !imageLabel.getText().isEmpty()) {
            String filePath = imageLabel.getText().substring(imageLabel.getText().indexOf("::") + 2);
            File file = new File(filePath);
            if (file.exists() && lsFile.contains(file.getName().substring(file.getName().lastIndexOf(".")))) {
                image = filePath;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid file type selected. Please select a .jpeg file.");
                alert.showAndWait();
                return;
            }
        }

        Products updateProducts = new Products(products.getProductId(), name, description, type, brand, year, quantity, price, image);


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
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductsList.fxml"));
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
    private  void handleClear(ActionEvent event) throws IOException{

        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        quantityField.clear();
        yearField.clear();
        typeField.clear();
        brandField.clear();
        imageLabel.setText("");
    }

    @FXML
    public void updateImage(ActionEvent event){
        FileChooser  fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg"));
        File f = fc.showOpenDialog(null);
        if(f != null){
            imageLabel.setText("Select File ::" + f.getAbsolutePath());
        }

    }

    public void setTable(TableView<Products> tableProducts) {
        this.tableProducts = tableProducts;
    }



}
