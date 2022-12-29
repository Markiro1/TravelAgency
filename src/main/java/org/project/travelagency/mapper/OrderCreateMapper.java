package org.project.travelagency.mapper;

import org.project.travelagency.dao.HotelDao;
import org.project.travelagency.dao.UserDao;
import org.project.travelagency.dao.impl.HotelDaoImpl;
import org.project.travelagency.dao.impl.RoomDaoImpl;
import org.project.travelagency.dao.impl.UserDaoImpl;
import org.project.travelagency.dto.order.OrderCreateDto;
import org.project.travelagency.model.Order;
import org.project.travelagency.model.Room;
import org.project.travelagency.service.HotelService;
import org.project.travelagency.service.RoomService;
import org.project.travelagency.service.UserService;
import org.project.travelagency.service.impl.HotelServiceImpl;
import org.project.travelagency.service.impl.RoomServiceImpl;
import org.project.travelagency.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class OrderCreateMapper {
    public static OrderCreateDto mapToDto(Order order) {
        return OrderCreateDto.builder()
                .orderDate(order.getOrderDate())
                .checkIn(order.getCheckIn().toString())
                .checkOut(order.getCheckOut().toString())
                .user(order.getUser())
                .hotel(order.getHotel().getName())
                .rooms(order.getReservedRooms())
                .amount(order.getAmount())
                .build();
    }

    public static Order mapToModel(OrderCreateDto orderDto) {
        HotelService hotelService = new HotelServiceImpl(new HotelDaoImpl());
        return Order.builder()
                .orderDate(orderDto.getOrderDate())
                .checkIn(LocalDate.parse(orderDto.getCheckIn()))
                .checkOut(LocalDate.parse(orderDto.getCheckOut()))
                .user(orderDto.getUser())
                .hotel(hotelService.getHotelByName(orderDto.getHotel()))
                .reservedRooms(orderDto.getRooms())
                .amount(orderDto.getAmount())
                .build();
    }
}
