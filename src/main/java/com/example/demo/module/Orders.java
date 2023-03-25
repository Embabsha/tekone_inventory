package com.example.demo.module;

import javafx.beans.property.*;

import java.util.Date;


public class Orders {
    private IntegerProperty orderId;
    private IntegerProperty productId;
    private IntegerProperty customerId;
    private IntegerProperty quantity;
    private SimpleDoubleProperty total;
    private IntegerProperty adminId;
    private StringProperty statues;
    private ObjectProperty<Date> date;

    public Orders(int orderId, int productId, int customerId, int quantity, double total, String statues, int adminId, Date date) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.productId = new SimpleIntegerProperty(productId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.total = new SimpleDoubleProperty(total);
        this.statues = new SimpleStringProperty(statues);
        this.adminId = new SimpleIntegerProperty(adminId);
        this.date = new SimpleObjectProperty<>(date);
    }

    public IntegerProperty getOrderIdProperty() { return orderId; }
    public IntegerProperty getProductIdProperty() { return productId; }
    public IntegerProperty getCustomerIdProperty() { return customerId; }
    public IntegerProperty getQuantityProperty() { return quantity; }
    public SimpleDoubleProperty getTotalProperty() { return total; }
    public StringProperty getStatuesProperty() { return statues; }
    public IntegerProperty getAdminIdProperty() { return adminId; }
    public ObjectProperty<Date> getDateProperty() { return date;}

    public String getStatues(){return statues.getValue();}
    public void setStatues(String statues){this.statues.set(statues);}

    public Integer getOrderId() {return  orderId.getValue();}
    public Integer getProductId() {return  productId.getValue();}
    public Integer getCustomerId() {return  customerId.getValue() ;}
    public Orders getOrders(){return this ;}
    public Integer getQuantity(){return quantity.getValue();}
    public Double getTotal(){return total.getValue();}
    public void setOrderId(int orderId) {this.orderId.set(orderId);}
    public void setQuantity(int quantity) {this.quantity.set(quantity);}
    public void setTotal(Double total) {this.total.set(total);}
    public Integer getAdminId() {return  adminId.getValue() ;}
    public void setAdminId(int adminId){ this.adminId.set(adminId); }

    public Date getDate() {return date.getValue();}

    public void setDate(Date date) {this.date.set(date);}

}
