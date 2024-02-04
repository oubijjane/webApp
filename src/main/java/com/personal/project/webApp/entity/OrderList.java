package com.personal.project.webApp.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;


    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name="product_id")
    private Product product;

    public OrderList() {

    }

    public OrderList(int quantity, Customer customer, Product product) {
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public double getSum(){
        double sum = product.getPrice() * quantity;
        sum = (double) (Math.round(sum*100.0)/100.0);

        return sum;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderList orderList = (OrderList) o;
        return id == orderList.id && quantity == orderList.quantity && Objects.equals(customer, orderList.customer) && Objects.equals(product, orderList.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, customer, product);
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", customer=" + customer +
                ", product=" + product +
                '}';
    }
}
