package com.personal.project.webApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

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



    @OneToMany(mappedBy = "product",
            cascade =  {CascadeType.MERGE, CascadeType.REMOVE})
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
        return getProductName() + "<br/>" + getDescription();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.price, price) == 0 && quantity == product.quantity && Objects.equals(productName, product.productName) && Objects.equals(pictureLocation, product.pictureLocation) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, price, pictureLocation, description, quantity);
    }
}
