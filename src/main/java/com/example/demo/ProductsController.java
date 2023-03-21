package com.example.demo;

import com.example.demo.module.Admin;
import com.example.demo.module.Products;
import com.example.demo.module.ProductsCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductsController {
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button Back;
    @FXML
    private TableView<Products> tableProducts;
    @FXML private TableColumn<Products, Integer> colProductId;
    @FXML private TableColumn<Products, String> colName;
    @FXML private TableColumn<Products, String> colDescription;
    @FXML private TableColumn<Products, String> colType;
    @FXML private TableColumn<Products, String> colBrand;
    @FXML private TableColumn<Products, Double> colYear;
    @FXML private TableColumn<Products, Integer> colQuantity;
    @FXML private TableColumn<Products, Double> colPrice;
    //@FXML private TableColumn<Products, ImageView> colImage;
    //@FXML private ImageView productImage;


    public void initialize(){ load();}

    @FXML
    private void handleBack(ActionEvent event ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

@FXML
private void handleAdd(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

}
    @FXML
    private void handleDelete(ActionEvent event) throws IOException {
        // Get the selected items from the table view
        ObservableList<Products> selectedItems = tableProducts.getSelectionModel().getSelectedItems();

        // Iterate over the selected items and delete them from the database
        for (Products products : selectedItems) {
            String query = "DELETE FROM product WHERE product_id = ?";
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, products.getProductId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        tableProducts.getItems().removeAll(selectedItems);
        tableProducts.refresh();
    }

    @FXML
    private void handleUpdate() throws IOException {
        Products selectedProduct = tableProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setContentText("Please select a product from the table.");
            alert.showAndWait();
            return;
        }
        final UpdateProductsController updateproductscontroller = new UpdateProductsController(selectedProduct);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateProduct.fxml"));
        fxmlLoader.setController(updateproductscontroller);
        Parent root =(Parent) fxmlLoader.load();
        updateproductscontroller.setTable(tableProducts);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    public void load() {
        colProductId.setCellValueFactory(cell -> cell.getValue().getProductIdProperty().asObject());
        colName.setCellValueFactory(cell -> cell.getValue().getNameProperty());
        colDescription.setCellValueFactory(cell -> cell.getValue().getDescriptionProperty());
        colType.setCellValueFactory(cell -> cell.getValue().getTypeProperty());
        colYear.setCellValueFactory(cell -> cell.getValue().getYearProperty().asObject());
        colBrand.setCellValueFactory(cell -> cell.getValue().getBrandProperty());
        colPrice.setCellValueFactory(cell -> cell.getValue().getPriceProperty().asObject());
        colQuantity.setCellValueFactory(cell -> cell.getValue().getQuantityProperty().asObject());
     //   colImage.setCellValueFactory(new PropertyValueFactory<>("photo"));

        ProductsCollection productsCollection = new ProductsCollection();
        ArrayList<Products> products = new ArrayList<>(productsCollection.getAllProducts());
        ObservableList<Products> data = FXCollections.observableArrayList(products);

        tableProducts.setItems(data);


    }

}
