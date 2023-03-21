package com.example.demo;

import com.example.demo.module.Admin;
import com.example.demo.module.Orders;
import com.example.demo.module.OrdersCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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
    final UpdateOrdersController updateorderscontroller = new UpdateOrdersController(table.getSelectionModel().getSelectedItem());
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateOrders.fxml"));
    fxmlLoader.setController(updateorderscontroller);
    Parent root = (Parent) fxmlLoader.load();
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




        OrdersCollection ordersCollection = new OrdersCollection();
        ArrayList<Orders> orders = new ArrayList<>(ordersCollection.getAllOrders());
        ObservableList<Orders> data = FXCollections.observableArrayList(orders);

        table.setItems(data);

    }




}
