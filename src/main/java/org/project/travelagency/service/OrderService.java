package org.project.travelagency.service;

import org.project.travelagency.dto.order.OrderCreateDto;
import org.project.travelagency.dto.order.OrderDto;
import org.project.travelagency.model.Order;

import java.util.List;

public interface OrderService {

    Order create(OrderCreateDto orderDto);

    Order readById(Long id);

    List<Order> readByUserId(Long id);

    List<Order> getAllOrders();

    Order update(OrderDto orderDto);

    void delete(Long id);
}