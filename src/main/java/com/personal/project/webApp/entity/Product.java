package com.personal.project.webApp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name")
    private  String productName;

    @Column(name = "price")
    private float price;

    @Column(name = "picture_location")
    private String pictureLocation;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @ManyToMany(mappedBy = "products")
    private List<Customer> customers;

    @OneToMany(mappedBy = "product",
            cascade =  CascadeType.MERGE)
    List<OrderList> orderLists;


    public Product() {

    }



    public Product(String productName, float price, String pictureLocation, String description,int quantity) {
        this.productName = productName;
        this.price = price;
        this.pictureLocation = pictureLocation;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPictureLocation() {
        return pictureLocation;
    }

    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String descrName() {
        System.out.println(getProductName() + ":\n" + getDescription());
        return getProductName() + "<br/>" + getDescription();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<OrderList> getOrders() {
        return orderLists;
    }

    public void setOrders(List<OrderList> orderLists) {
        this.orderLists = orderLists;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", pictureLocation='" + pictureLocation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
