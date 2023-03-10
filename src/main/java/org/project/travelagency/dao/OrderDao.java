package org.project.travelagency.dao;

import org.project.travelagency.model.Order;

import java.util.List;

public interface OrderDao {

    Order save(Order order);

    Order getById(Long id);

    List<Order> getAll();

    Order update(Order order);

    void delete(Order order);
}