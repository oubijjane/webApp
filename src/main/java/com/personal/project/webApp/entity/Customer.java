package com.personal.project.webApp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;
    @Column(name="address")
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name="email")
    private String email;

    @Column
    private String role;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = CascadeType.MERGE)
    @JoinTable(
            name = "cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    List<OrderList> orderLists;
    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.role = "CUSTOMER";
        this.password = "{noop}"+password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = "ROLE_" + role;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        if(products == null) {
            products = new ArrayList<>();
        }
        this.products = products;
    }

    public void addToCart(Product product){
        products.add(product);
    }

    public List<OrderList> getOrders() {
        return orderLists;
    }

    public void setOrders(List<OrderList> orderLists) {
        this.orderLists = orderLists;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}


