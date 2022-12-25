package org.project.travelagency.service.impl;

import org.project.travelagency.dao.OrderDao;
import org.project.travelagency.dto.order.OrderDto;
import org.project.travelagency.exception.NullEntityReferenceException;
import org.project.travelagency.mapper.OrderMapper;
import org.project.travelagency.model.Order;
import org.project.travelagency.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order create(OrderDto orderDto) {
        if (orderDto != null) {
            Order order = OrderMapper.mapToModel(orderDto);
            return orderDao.save(order);
        } else {
            throw new NullEntityReferenceException("Order cannot be 'null'");
        }
    }

    @Override
    public Order readById(Long id) {
        Order order = orderDao.getById(id);
        if (order != null) {
            return order;
        } else {
            throw new NullEntityReferenceException("Order not found");
        }
    }

    @Override
    public List<Order> readByUserId(Long id) {
        return orderDao.getAll().stream().filter(o -> o.getUser().getId() == id).collect(Collectors.toList());
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAll();
    }

    @Override
    public Order update(OrderDto orderDto) {
        if (orderDto != null) {
            Order order = OrderMapper.mapToModel(orderDto);
            return orderDao.update(order);
        } else {
            throw new NullEntityReferenceException("Order cannot be 'null'");
        }
    }

    @Override
    public void delete(Long id) {
        Order order = readById(id);
        orderDao.delete(order);
    }
}