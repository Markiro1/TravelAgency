package org.project.travelagency.service;

import org.project.travelagency.dto.order.OrderDto;
import org.project.travelagency.model.Order;

import java.util.List;

public interface OrderService {
    void create(OrderDto orderDto);
    void delete(Long id);
    void update(OrderDto orderDto);
    Order readById(Long id);
    List<Order> getAllOders();
    List<Order> getByUserId(Long id);
}
