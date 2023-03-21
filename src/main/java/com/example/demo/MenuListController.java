package com.example.demo;

import com.example.demo.module.Orders;
import com.example.demo.module.OrdersCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class MenuListController {
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button Back;
    @FXML
    private NumberAxis caOrders;
    @FXML
    private CategoryAxis caProducts;
    @FXML
    private BarChart<Integer, Integer> barChart;
    private Connection connection;
    private ResultSet resultSet;

    private OrdersCollection orders;



    public void initialize() {

        orders = new OrdersCollection(); // initialize the OrdersCollection object
        List<Orders> ordersList = orders.getAllOrders();

        // Prepare the data for the bar chart
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        series.setName("Products");
        for (Orders orders : ordersList) {
            series.getData().add(new XYChart.Data<>(orders.getProductId(), orders.getQuantity()));
        }

        // Configure the bar chart
        caProducts.setLabel("Products");
        caOrders.setLabel("Orders");
        barChart.getData().add(series);

    }
}

