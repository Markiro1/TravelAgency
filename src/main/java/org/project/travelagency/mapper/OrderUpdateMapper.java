package org.project.travelagency.mapper;

import org.project.travelagency.dao.impl.HotelDaoImpl;
import org.project.travelagency.dto.order.OrderUpdateDto;
import org.project.travelagency.model.Order;
import org.project.travelagency.service.HotelService;
import org.project.travelagency.service.impl.HotelServiceImpl;

import java.time.LocalDate;

public class OrderUpdateMapper {

    public static OrderUpdateDto mapToDto(Order order) {
        return OrderUpdateDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .checkIn(order.getCheckIn().toString())
                .checkOut(order.getCheckOut().toString())
                .user(order.getUser())
                .hotel(order.getHotel())
                .rooms(order.getReservedRooms())
                .amount(order.getAmount())
                .build();
    }

    public static Order mapToModel(OrderUpdateDto orderDto) {
        HotelService hotelService = new HotelServiceImpl(new HotelDaoImpl());
        return Order.builder()
                .id(orderDto.getId())
                .orderDate(orderDto.getOrderDate())
                .checkIn(LocalDate.parse(orderDto.getCheckIn()))
                .checkOut(LocalDate.parse(orderDto.getCheckOut()))
                .user(orderDto.getUser())
                .hotel(hotelService.readById(orderDto.getHotel().getId()))
                .reservedRooms(orderDto.getRooms())
                .amount(orderDto.getAmount())
                .build();
    }
}