package com.example.demo.module;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminCollectionTest {

    private AdminCollection adminCollection;

    @BeforeEach
    public void setUp() {
        adminCollection = new AdminCollection(new ArrayList<>());
    }

    @Test
    public void testAddAdmin() {
        // create a new admin to add
        Admin newAdmin = new Admin(9, "John Doe", "johndoe@example.com", "password", "123 Main St", "555-555-5555");

        // add the admin to the collection
        adminCollection.addAdmin(newAdmin);

        // verify that the admin was added successfully
        List<Admin> allAdmins = adminCollection.getAllAdmins();

    }

    @Test
    public void testGetAllAdmins() {
        // add some admins to the collection
        Admin admin1 = new Admin(9, "John Doe", "johndoe@example.com", "password", "123 Main St", "555-555-5555");
        Admin admin2 = new Admin(9, "Jane Smith", "janesmith@example.com", "password", "456 Oak St", "555-555-5555");
        adminCollection.addAdmin(admin1);
        adminCollection.addAdmin(admin2);

        // verify that all admins can be retrieved from the collection
        List<Admin> allAdmins = adminCollection.getAllAdmins();

    }

    @Test
    public void testUpdateAdmin() {
        // add an admin to the collection
        Admin admin = new Admin(9, "John Doe", "johndoe@example.com", "password", "123 Main St", "555-555-5555");
        adminCollection.addAdmin(admin);

        // update the admin's information
        admin.setName("Jane Smith");
        admin.setEmail("janesmith@example.com");
        admin.setAddress("456 Oak St");
        admin.setPhone("555-555-5555");
        adminCollection.updateAdmin(admin);

        // verify that the admin's information was updated successfully
        List<Admin> allAdmins = adminCollection.getAllAdmins();

    }

    @Test
    public void testDeleteAdmin() {
        // add an admin to the collection
        Admin admin = new Admin(9, "John Doe", "johndoe@example.com", "password", "123 Main St", "555-555-5555");
        adminCollection.addAdmin(admin);

        // delete the admin from the collection
        adminCollection.deleteAdmin(admin.getAdminId());

        // verify that the admin was deleted successfully
        List<Admin> allAdmins = adminCollection.getAllAdmins();
    }

    @Test
    void testSearchAdmin() {
        // Create a list of admins to search
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin(30,"John Doe", "john@example.com", "password123", "123 Main St", "555-555-5555"));
        admins.add(new Admin(31,"Jane Smith", "jane@example.com", "password456", "456 Oak St", "555-555-1234"));
        admins.add(new Admin(32,"Bob Johnson", "bob@example.com", "password789", "789 Pine St", "555-555-6789"));

        // Add the admins to the collection
        for (Admin admin : admins) {
            adminCollection.addAdmin(admin);
        }

        // Search for an admin by name
        TextField keyword = new TextField("John");
        List<Admin> searchResults = adminCollection.searchAdmin(keyword);


        // Search for an admin by email
        keyword.setText("jane@example.com");
        searchResults = adminCollection.searchAdmin(keyword);


        // Search for an admin by address
        keyword.setText("789 Pine St");
        searchResults = adminCollection.searchAdmin(keyword);


        // Search for an admin by phone
        keyword.setText("555-555-1234");
        searchResults = adminCollection.searchAdmin(keyword);


        // Search for an admin that doesn't exist
        keyword.setText("Nonexistent Admin");
        searchResults = adminCollection.searchAdmin(keyword);
    }


}