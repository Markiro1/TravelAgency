package org.project.travelagency.mapper;

import org.project.travelagency.dao.impl.HotelDaoImpl;
import org.project.travelagency.dto.order.OrderCreateDto;
import org.project.travelagency.model.Order;
import org.project.travelagency.service.HotelService;
import org.project.travelagency.service.impl.HotelServiceImpl;

import java.time.LocalDate;

public class OrderCreateMapper {
    public static OrderCreateDto mapToDto(Order order) {
        return OrderCreateDto.builder()
                .orderDate(order.getOrderDate())
                .checkIn(order.getCheckIn().toString())
                .checkOut(order.getCheckOut().toString())
                .user(order.getUser())
                .hotel(order.getHotel())
                .rooms(order.getReservedRooms())
                .amount(order.getAmount())
                .build();
    }

    public static Order mapToModel(OrderCreateDto orderDto) {
        return Order.builder()
                .orderDate(orderDto.getOrderDate())
                .checkIn(LocalDate.parse(orderDto.getCheckIn()))
                .checkOut(LocalDate.parse(orderDto.getCheckOut()))
                .user(orderDto.getUser())
                .hotel(orderDto.getHotel())
                .reservedRooms(orderDto.getRooms())
                .amount(orderDto.getAmount())
                .build();
    }
}
