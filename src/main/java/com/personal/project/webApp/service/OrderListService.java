package com.personal.project.webApp.service;

import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;

import java.util.List;

public interface OrderListService {
    List<OrderList> findAll();

    OrderList findById(int id);

    void save(OrderList orderList);

    void deleteById(int id);


}
