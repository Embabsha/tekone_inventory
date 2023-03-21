package com.example.demo.module;

import javafx.beans.property.*;

public class Orders {

    private IntegerProperty orderId;
    private IntegerProperty productId;
    private IntegerProperty customerId;
    private IntegerProperty quantity;
    private SimpleDoubleProperty total;
    private IntegerProperty adminId;
    private StringProperty statues;
    public Orders(int orderId, int productId, int customerId, int quantity, double total,String statues,int adminId) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.productId = new SimpleIntegerProperty(productId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.total = new SimpleDoubleProperty(total);
        this.statues = new SimpleStringProperty(statues);
        this.adminId = new SimpleIntegerProperty(adminId);
    }

    public SimpleIntegerProperty getOrderIdProperty(){return new SimpleIntegerProperty(orderId.getValue());}
    public SimpleIntegerProperty getProductIdProperty(){return new SimpleIntegerProperty(productId.getValue());}
    public SimpleIntegerProperty getCustomerIdProperty(){return new SimpleIntegerProperty(customerId.getValue());}
    public SimpleIntegerProperty getQuantityProperty(){return new SimpleIntegerProperty(quantity.getValue());}
    public SimpleDoubleProperty getTotalProperty(){return new SimpleDoubleProperty(total.getValue());}

    public SimpleStringProperty getStatuesProperty(){return new SimpleStringProperty(statues.getValue());}
    public SimpleIntegerProperty getAdminIdProperty(){return new SimpleIntegerProperty(adminId.getValue());}

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


}
