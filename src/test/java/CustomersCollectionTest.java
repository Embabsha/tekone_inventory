import com.example.demo.DatabaseConnection;
import com.example.demo.module.CustomersCollection;
import com.example.demo.module.Customer;

import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CustomersCollectionTest {

    private Connection connection;
    private CustomersCollection customersCollection;
    private List<Customer> customerList;

    @Before
    public void setUp() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
        customersCollection = new CustomersCollection(new ArrayList<>());
    }

    @Test
    public void testAddCustomer() throws SQLException {
        // Create a new customer object with sample data
        Customer customer = new Customer(7, "John", "john@example.com", "password", "123 Main St", "555-1234");

        // Call the addCustomer method to add the customer to the database
        customersCollection.addCustomer(customer);

        // Check that the customer was added correctly by querying the database
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE email = ?");
        statement.setString(1, customer.getEmail());
        ResultSet resultSet = statement.executeQuery();

        // Verify that a row was returned with the correct data
        if (resultSet.next()) {
            int customerId = resultSet.getInt("customer_id");
            assertEquals(customer.getName(), resultSet.getString("name"));
            assertEquals(customer.getEmail(), resultSet.getString("email"));
            assertEquals(customer.getPassword(), resultSet.getString("password"));
            assertEquals(customer.getAddress(), resultSet.getString("address"));
            assertEquals(customer.getPhone(), resultSet.getString("phone"));

            // Verify that the customer_id was auto-generated
        } else {
            throw new SQLException("No rows found in customers table");
        }
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(new Customer(7,"John", "john@example.com", "password", "123 Main St", "555-1234"));
        expectedCustomers.add(new Customer(7,"Jane", "jane@example.com", "password", "456 Oak St", "555-5678"));

        // Add the expected customers to the database
        for (Customer customer : expectedCustomers) {
            customersCollection.addCustomer(customer);
        }

        List<Customer> actualCustomers = customersCollection.getAllCustomers();
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer(7,"John", "john@example.com", "password", "123 Main St", "555-1234");
        customersCollection.addCustomer(customer);

        // Update the customer's name
        String newName = "John Doe";
        customer.setName(newName);
        customersCollection.updateCustomer(customer);

        // Retrieve the updated customer from the database
        List<Customer> actualCustomers = customersCollection.getAllCustomers();
        Customer actualCustomer = actualCustomers.get(0);
    }




    }




