package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.OrderListDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderListServiceImpl implements OrderListService{

    private OrderListDAO orderListDAO;

    @Autowired
    public OrderListServiceImpl(OrderListDAO orderListDAO) {
        this.orderListDAO = orderListDAO;
    }

    @Override
    public List<OrderList> findAll() {
        return orderListDAO.findAll();
    }

    @Override
    public OrderList findById(int id) {
        Optional<OrderList> result = orderListDAO.findById(id);
        OrderList orderList = null;
        if (result.isPresent()) {
            orderList = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find orderList id - " + id);
        }
        return orderList;
    }

    @Override
    public void save(OrderList orderList) {
        orderListDAO.save(orderList);

    }

    @Override
    public void deleteById(int id) {
        orderListDAO.deleteById(id);
    }


}
