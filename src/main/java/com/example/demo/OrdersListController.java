package com.example.demo;

import com.example.demo.module.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class OrdersListController  {
@FXML
private Button delete;
@FXML
private Button add;
@FXML
private Button update;
@FXML
private Button Back;

    @FXML private TableView<Orders> table;
    @FXML private TableColumn<Orders,Integer> colAdminId;
    @FXML private TableColumn<Orders,Integer> colOrderId;
    @FXML private TableColumn<Orders,Integer> colCustomerId;
    @FXML private TableColumn<Orders,Integer> colProductId;

    @FXML private TableColumn<Orders,Integer> colQuantity;
    @FXML private TableColumn<Orders,Double> colTotal;
    @FXML private TableColumn<Orders,String> colStatues;
    @FXML private TableColumn<Orders, Date> colDate;
    @FXML private TextField searchField;
    @FXML private  ComboBox filter ;
    @FXML private ComboBox sort;

    OrdersCollection ordersCollection = new OrdersCollection();
    public void initialize()  {load();

        filter.setItems(FXCollections.observableArrayList("All", "Pending", "Shipped", "Delivered"));
        filter.setValue("All");

        sort.setItems(FXCollections.observableArrayList("Newest","Oldest"));
        sort.setValue("Newest");
    }


@FXML
private void handleBack(ActionEvent event ) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();


}
    public void handelSortByDate(ActionEvent event){
        sort.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Newest")) {
                // Sort by newest date
                table.getItems().sort(Comparator.comparing(Orders::getDate).reversed());
            } else if(newValue.equals("Oldest")) {
                // Sort by oldest date
                table.getItems().sort(Comparator.comparing(Orders::getDate));
            }
        });

    }
    public void handleFilterByStatus(ActionEvent event) {
        String status = (String) filter.getValue();
        List<Orders> filteredOrders;
        if (status.equals("All")) {
            filteredOrders = ordersCollection.getAllOrders(); // load all orders
        } else {
            filteredOrders = ordersCollection.filterOrdersByStatus(status);
        }
        ObservableList<Orders> data = FXCollections.observableArrayList(filteredOrders);
        table.setItems(data);
    }
    public void handelSearch(ActionEvent event) throws IOException{
        OrdersCollection ordersCollection = new OrdersCollection();
        ArrayList<Orders> orders = new ArrayList<>(ordersCollection.searchOrders(searchField));
        ObservableList<Orders> data = FXCollections.observableArrayList(orders);
        table.setItems(data);

    }


@FXML
private void handleAdd(ActionEvent event) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddOrders.fxml"));
    Parent root = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

}
@FXML
private void handleDelete(ActionEvent event) throws IOException{
    ObservableList<Orders> selectedItems = table.getSelectionModel().getSelectedItems();

    // Iterate over the selected items and delete them from the database
    for (Orders orders : selectedItems) {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orders.getOrderId());
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
    public void handleUpdate() throws IOException {
        Orders selectedOrder = table.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setContentText("Please select a product from the table.");
            alert.showAndWait();
            return;
        }
        final UpdateOrdersController updateOrdersController = new UpdateOrdersController(selectedOrder);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateOrders.fxml"));
        fxmlLoader.setController(updateOrdersController);
        Parent root =(Parent) fxmlLoader.load();
        updateOrdersController.setTable(table);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();

    }



    @FXML
    public void load() {
        colOrderId.setCellValueFactory(cell -> cell.getValue().getOrderIdProperty().asObject());
        colCustomerId.setCellValueFactory(cell -> cell.getValue().getCustomerIdProperty().asObject());
        colAdminId.setCellValueFactory(cell -> cell.getValue().getAdminIdProperty().asObject());
        colProductId.setCellValueFactory(cell -> cell.getValue().getProductIdProperty().asObject());
        colQuantity.setCellValueFactory(cell -> cell.getValue().getQuantityProperty().asObject());
        colTotal.setCellValueFactory(cell -> cell.getValue().getTotalProperty().asObject());
        colStatues.setCellValueFactory(cell -> cell.getValue().getStatuesProperty());
        colDate.setCellValueFactory(cell -> cell.getValue().getDateProperty());








        OrdersCollection ordersCollection = new OrdersCollection();
        ArrayList<Orders> orders = new ArrayList<>(ordersCollection.getAllOrders());
        ObservableList<Orders> data = FXCollections.observableArrayList(orders);

        table.setItems(data);

    }




}
