package com.example.demo.module;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.sql.Blob;
import java.sql.SQLException;

public class Products {

    @FXML
    private SimpleIntegerProperty productId;
    @FXML
    private SimpleStringProperty name;
    @FXML
    private SimpleStringProperty description;
    @FXML
    private SimpleStringProperty type;
    @FXML
    private SimpleStringProperty brand;
    @FXML
    private SimpleDoubleProperty year;
    @FXML
    private SimpleIntegerProperty quantity;
    @FXML
    private SimpleDoubleProperty price;
    //@FXML
    @FXML
    private SimpleStringProperty image;

    private TableView<Products> tableProducts;

    public Products(int productId, String name, String description, String type, String brand, Double year, int quantity, Double price, String image) {
        this.productId = new SimpleIntegerProperty(productId);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.type = new SimpleStringProperty(type);
        this.brand = new SimpleStringProperty(brand);
        this.year = new SimpleDoubleProperty(year);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.image = new SimpleStringProperty(image);
    }

    public SimpleStringProperty getNameProperty() {
        return new SimpleStringProperty(name.getValue());
    }

    public SimpleIntegerProperty getProductIdProperty() {
        return new SimpleIntegerProperty(productId.getValue());
    }

    public SimpleStringProperty getTypeProperty() {
        return new SimpleStringProperty(type.getValue());
    }

    public SimpleStringProperty getDescriptionProperty() {
        return new SimpleStringProperty(description.getValue());
    }

    public SimpleDoubleProperty getYearProperty() {
        return new SimpleDoubleProperty(year.getValue());
    }

    public SimpleStringProperty getBrandProperty() {
        return new SimpleStringProperty(brand.getValue());
    }

    public SimpleIntegerProperty getQuantityProperty() {
        return new SimpleIntegerProperty(quantity.getValue());
    }

    public SimpleDoubleProperty getPriceProperty() {
        return new SimpleDoubleProperty(price.getValue());
    }

    public SimpleStringProperty getImageProperty() {
        return image;
    }

    public String getImage() {
        return image.getValue();
    }

    public void setImage(String image) {
        this.image.set(image);
    }


    public Products getProducts() {
        return this;
    }

    public Integer getProductId() {
        return productId.getValue();
    }

    public Integer getQuantity() {
        return quantity.getValue();
    }

    public Double getPrice() {
        return price.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

    public String getBrand() {
        return brand.getValue();
    }

    public String getType() {
        return type.getValue();
    }

    public Double getYear() {
        return year.getValue();
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setYear(Double year) {
        this.year.set(year);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
    //  public void setImage(Blob image) {this.image = image;}


}