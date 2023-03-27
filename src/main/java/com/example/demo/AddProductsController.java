package com.example.demo;

import com.example.demo.module.Products;
import com.example.demo.module.ProductsCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddProductsController {
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button back;
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    private TextField imageString;

    @FXML
    private Button btnSinglerFile;
    @FXML
    private Label labelSingleFile;
     List<String> lsFile;

    private ProductsCollection productsCollection = new ProductsCollection();



// ...

    @FXML
    private void handleAdd(ActionEvent event) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String type = typeField.getText();
        String brand = brandField.getText();
        double year = Double.parseDouble(yearField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        String image = "";
        if (labelSingleFile.getText() != null && !labelSingleFile.getText().isEmpty()) {
            String filePath = labelSingleFile.getText().substring(labelSingleFile.getText().indexOf("::") + 2);
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
        // Create a new Products object
        Products products = new Products(0, name, description, type, brand, year, quantity, price, image);

        // Add the new product to the database
        productsCollection.addProducts(products);

        // Display a success message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product Added");
        alert.setHeaderText(null);
        alert.setContentText("The product has been added to the database.");
        alert.showAndWait();

        // Clear the input fields
        nameField.clear();
        descriptionField.clear();
        typeField.clear();
        brandField.clear();
        yearField.clear();
        quantityField.clear();
        priceField.clear();
        labelSingleFile.setText("");
    }



   @FXML
   private void singelFileChooser(ActionEvent event){
    FileChooser  fc = new FileChooser();
    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg"));
    File f = fc.showOpenDialog(null);
    if(f != null){
        labelSingleFile.setText("Select File ::" + f.getAbsolutePath());
    }


   }
   public void initialize(){
        lsFile = new ArrayList<>();
        lsFile.add(".jpeg");
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
    labelSingleFile.setText("");
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


}
