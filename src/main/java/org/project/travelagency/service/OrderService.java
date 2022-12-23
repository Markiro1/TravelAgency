package org.project.travelagency.service;

import org.project.travelagency.model.Order;

import java.util.List;

public interface OrderService {
    void create(Order order);
    void delete(Long id);
    void update(Order order);
    Order readById(Long id);
    List<Order> getAllOders();
    List<Order> getByUserId(Long id);
}
