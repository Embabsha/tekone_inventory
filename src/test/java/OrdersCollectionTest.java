package com.example.demo.module;

import com.example.demo.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrdersCollectionTest {

    private OrdersCollection ordersCollection;
    private Connection connection;

    @BeforeEach
    void setUp() {
        // Set up a new OrdersCollection and a database connection
        List<Orders> ordersList = new ArrayList<>();
        ordersCollection = new OrdersCollection(ordersList);
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Test
    void testAddOrders() {
        // Add a new order to the database
        Orders order = new Orders(1, 1, 1, 1, 2,"Pending", 1, Date.valueOf(LocalDate.now()));
        ordersCollection.addOrders(order);

        // Check if the order was added successfully
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE order_id=1");
            Assertions.assertTrue(resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetAllOrders() {
        // Add some test orders to the database
        Orders order1 = new Orders(1, 1, 1, 1, "Pending", 1, Date.valueOf(LocalDate.now()));
        Orders order2 = new Orders(2, 2, 2, 2, "Delivered", 2, Date.valueOf(LocalDate.now()));
        Orders order3 = new Orders(3, 3, 3, 3, "Cancelled", 3, Date.valueOf(LocalDate.now()));
        ordersCollection.addOrders(order1);
        ordersCollection.addOrders(order2);
        ordersCollection.addOrders(order3);

        // Get all orders from the database
        List<Orders> ordersList = ordersCollection.getAllOrders();

        // Check if the orders were retrieved successfully
        assertEquals(3, ordersList.size());
        assertEquals(order1, ordersList.get(0));
        assertEquals(order2, ordersList.get(1));
        assertEquals(order3, ordersList.get(2));
    }

    @Test
    void testFilterOrdersByStatus() {
        // Add some test orders to the database
        Orders order1 = new Orders(1, 1, 1, 1, 1,"Pending", 1,null);
        Orders order2 = new Orders(2, 2, 2, 2, 2,"Delivered",  2,null);
        Orders order3 = new Orders(3, 3, 3, 3, 2,"Cancelled", 3, null);
        ordersCollection.addOrders(order1);
        ordersCollection.addOrders(order2);
        ordersCollection.addOrders(order3);

        // Filter orders by status
        List<Orders> filteredOrders = ordersCollection.filterOrdersByStatus("Pending");

        // Check if the orders were filtered successfully
        assertEquals(1, filteredOrders.size());
        assertEquals(order1, filteredOrders.get(0));
    }

    @Test
    void testSearchOrders() {
        // Add some test orders to the database
        Orders order1 = new Orders(0, 1, 1, 1, 1, "Pending", 1, null);
        Orders order2 = new Orders(0, 2, 2, 2, 2, "Delivered", 2, null);
        Orders order3 = new Orders(0, 3, 3, 3, 2, "Cancelled", 3, null);

        // Search for orders by status
        List<Orders> pendingOrders = ordersCollection.searchOrders("Pending");
        List<Orders> deliveredOrders = ordersCollection.searchOrders("Delivered");
        List<Orders> cancelledOrders = ordersCollection.searchOrders("Cancelled");

        // Verify that the correct orders were returned
        assertEquals(1, pendingOrders.size());
        assertEquals(2, deliveredOrders.size());
        assertEquals(1, cancelledOrders.size());

        // Verify that the order details are correct
        assertEquals(order1, pendingOrders.get(0));
        assertEquals(order2, deliveredOrders.get(0));
        assertEquals(order3, cancelledOrders.get(0));
    }

}