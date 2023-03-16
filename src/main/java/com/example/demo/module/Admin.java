package com.example.demo.module;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;

public class Admin {
    private IntegerProperty adminId;
    private StringProperty name;
    private StringProperty email;
    private StringProperty password;
    private StringProperty address;
    private StringProperty phone;

    public Admin(int adminId, String name, String email, String password, String address, String phone) {
        this.adminId = new SimpleIntegerProperty(adminId);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
    }

    public SimpleIntegerProperty getAdminIdProperty() {
        return new SimpleIntegerProperty(adminId.getValue()) ;
    }
    public SimpleStringProperty getNameProperty() {
        return new SimpleStringProperty(name.getValue()) ;
    }
    public SimpleStringProperty getEmailProperty() {
        return new SimpleStringProperty(email.getValue()) ;
    }
    public SimpleStringProperty getPasswordProperty() {
        return new SimpleStringProperty(password.getValue()) ;
    }
    public SimpleStringProperty getAddressProperty() {
        return new SimpleStringProperty(address.getValue()) ;
    }
    public SimpleStringProperty getPhoneProperty() {
        return new SimpleStringProperty(phone.getValue()) ;
    }
    public Integer getAdminId() {
        return  adminId.getValue() ;
    }

    public String getName() {
        return name.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getPhone() {
        return phone.getValue();
    }
}
