package org.project.travelagency.mapper;

import org.project.travelagency.dto.order.OrderDto;
import org.project.travelagency.model.Order;

public class OrderMapper {
    public static OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .checkIn(order.getCheckIn())
                .checkOut(order.getCheckOut())
                .user(order.getUser())
                .hotel(order.getHotel())
                .rooms(order.getReservedRooms())
                .build();
    }

    public static Order mapToModel(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .orderDate(orderDto.getOrderDate())
                .checkIn(orderDto.getCheckIn())
                .checkOut(orderDto.getCheckOut())
                .user(orderDto.getUser())
                .hotel(orderDto.getHotel())
                .reservedRooms(orderDto.getRooms())
                .build();
    }
}