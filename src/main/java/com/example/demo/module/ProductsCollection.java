package com.example.demo.module;

import com.example.demo.DatabaseConnection;
import javafx.scene.control.TableView;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsCollection {
    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private List<Products> productsList;


    public ProductsCollection(List<Products> productsList) {
        this.productsList = productsList;
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public ProductsCollection() {
        this(new ArrayList<>());
        this.connection = DatabaseConnection.getInstance().getConnection();
    }


    public void addProducts(Products products) {
        String query = "INSERT INTO product (name, description, type, brand, year, quantity, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, products.getName());
            preparedStatement.setString(2, products.getDescription());
            preparedStatement.setString(3, products.getType());
            preparedStatement.setString(4, products.getBrand());
            preparedStatement.setDouble(5, products.getYear());
            preparedStatement.setInt(6, products.getQuantity());
            preparedStatement.setDouble(7, products.getPrice());
            //preparedStatement.setObject(8, product.getImage());
            int rowsInserted = preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {

                int productsId = generatedKeys.getInt(1);
                products.setProductId(productsId);

            }
        }catch(SQLException e){
                e.printStackTrace();
            }
    }

    public List<Products> getAllProducts() {


        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM product");
            while (resultSet.next()) {

                Products products = new Products(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("type"),
                        resultSet.getString("brand"),
                        resultSet.getDouble("year"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price")
                        //image
                );
                productsList.add(products);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }


    public void deleteProducts(int product_id) {
        String query = "DELETE FROM product WHERE product_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateProducts(int productId, Products products) {
        String query = "UPDATE product SET name=?, description=?, type=?, brand=?, year=?, quantity=?, price=? WHERE product_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, products.getName());
            preparedStatement.setString(2, products.getDescription());
            preparedStatement.setString(3, products.getType());
            preparedStatement.setString(4, products.getBrand());
            preparedStatement.setDouble(5, products.getYear());
            preparedStatement.setInt(6, products.getQuantity());
            preparedStatement.setDouble(7, products.getPrice());
            preparedStatement.setInt(8, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL error here");
        }
    }



}
