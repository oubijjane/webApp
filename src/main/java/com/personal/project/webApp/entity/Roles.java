package com.personal.project.webApp.entity;

import jakarta.persistence.*;
import jakarta.validation.Constraint;

import java.util.List;

@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private String email;

    @Column()
    private String role;

    public Roles() {
    }

    public Roles(Customer customer, String role) {

        this.role = role;
        this.customer = customer;
        this.email = customer.getEmail();
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomerList(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "customerList=" + customer +
                ", role='" + role + '\'' +
                '}';
    }
}
