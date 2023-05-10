package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderListDAO extends JpaRepository<OrderList, Integer> {
}
