package org.project.travelagency.controller.utils;

import org.project.travelagency.dto.order.OrderCreateDto;
import org.project.travelagency.model.Order;
import org.project.travelagency.model.Role;
import org.project.travelagency.model.Room;
import org.project.travelagency.model.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ControllerUtils {

    public static int intervalDays(LocalDate in, LocalDate out) {
        return (int) in.until(out, ChronoUnit.DAYS);
    }


    public static Boolean isValidDates(String in, String out) {
        if (in.isBlank() || out.isBlank()) return false;
        return intervalDays(LocalDate.parse(in), LocalDate.parse(out)) >= 0;
    }


    public static List<Order> getOrdersByHotelAtDates(List<Order> orders,
                                                      OrderCreateDto orderDto,
                                                      String dayIn,
                                                      String dayOut) {
        return orders.stream()
                .filter(order -> order.getHotel().getName().equals(orderDto.getHotel()))
                .filter(order ->
                        (isValidDates(order.getCheckOut().toString(), dayOut)
                                && isValidDates(dayIn, order.getCheckOut().toString()))
                                || (isValidDates(order.getCheckIn().toString(), dayOut)
                                && isValidDates(dayIn, order.getCheckIn().toString()))
                )
                .toList();
    }


    public static List<Room> getReservedRoomsByHotelAtDates(List<Order> ordersByHotelAtDates) {
        return ordersByHotelAtDates.stream()
                .map(Order::getReservedRooms)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }


    public static List<Room> getFreeRoomsByHotelAtDates(List<Room> reservedRoomsByHotelAtDates, List<Room> allRoomsByHotel) {
        return allRoomsByHotel.size() == reservedRoomsByHotelAtDates.size()
                ? null
                : allRoomsByHotel.stream()
                .filter(r -> !reservedRoomsByHotelAtDates.contains(r))
                .toList();

    }

}
