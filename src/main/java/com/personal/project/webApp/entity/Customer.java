package com.personal.project.webApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name="first_name")
    private String firstName;

    @NotBlank
    @Column(name="last_name")
    private String lastName;
    @NotBlank
    @Column(name="address")
    private String address;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank
    @Column(name="email", unique = true)
    private String email;


    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<OrderList> orderLists;

    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Roles> roles;

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.password = password;
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
                ", email='" + email + "}"
                ;
    }
}


