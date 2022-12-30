package org.project.travelagency.controller;

import org.project.travelagency.dto.order.OrderCreateDto;
import org.project.travelagency.dto.order.OrderUpdateDto;
import org.project.travelagency.mapper.OrderUpdateMapper;
import org.project.travelagency.model.Order;
import org.project.travelagency.model.Room;
import org.project.travelagency.service.HotelService;
import org.project.travelagency.service.OrderService;
import org.project.travelagency.service.RoomService;
import org.project.travelagency.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final HotelService hotelService;
    private final RoomService roomService;


    public OrderController(UserService userService, OrderService orderService, HotelService hotelService, RoomService roomService) {
        this.userService = userService;
        this.orderService = orderService;
        this.hotelService = hotelService;
        this.roomService = roomService;
    }


    @GetMapping("/create/{user_id}")
    public String create(@PathVariable("user_id") long userId, Model model) {

        OrderCreateDto orderDto = new OrderCreateDto();
        orderDto.setUser(userService.readById(userId));
        model.addAttribute("order", orderDto);
        model.addAttribute("countries", hotelService.getAllCountries());
        return "create-order";
    }


    @PostMapping("/create/{user_id}")
    public String create(@PathVariable("user_id") long userId,
                         @Validated @ModelAttribute("orderDto") OrderCreateDto orderDto,
                         Model model,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "create-order";
        }

        String dayIn = orderDto.getCheckIn();
        String dayOut = orderDto.getCheckOut();
        LocalDateTime now = LocalDateTime.now();

        orderDto.setUser(userService.readById(userId));

        if (orderDto.getCountry() != null && isValidDates(now.toLocalDate().toString(), dayIn) & isValidDates(dayIn, dayOut)){

            if (orderDto.getHotel() != null) {

                List<Order> ordersByHotelAtDates = getOrdersByHotelAtDates(orderService.getAllOrders(), orderDto, dayIn, dayOut);
                List<Room> reservedRoomsByHotelAtDates = getReservedRoomsByHotelAtDates(ordersByHotelAtDates);
                List<Room> allRoomsByHotel = roomService.getRoomsByHotelId(orderDto.getHotel().getId());
                List<Room> freeRoomsByHotel = getFreeRoomsByHotelAtDates(reservedRoomsByHotelAtDates, allRoomsByHotel);

                if (freeRoomsByHotel != null) {
                    model.addAttribute("freeRoomsNumber", IntStream.range(1, freeRoomsByHotel.size() + 1).toArray());
                    model.addAttribute("price", freeRoomsByHotel.get(0).getPrice());
                } else {
                    model.addAttribute("freeRoomsNumber", new ArrayList<>());
                    model.addAttribute("price", null);
                    model.addAttribute("message", "There are no available rooms");
                }

                int reservedRoomsCount = orderDto.getReservedRoomsCount();

                if (reservedRoomsCount > 0) {

                    List<Room> rooms = freeRoomsByHotel.stream().limit(reservedRoomsCount).toList();

                    double amount = reservedRoomsCount * rooms.get(0).getPrice() * (
                            intervalDays(LocalDate.parse(dayIn), LocalDate.parse(dayOut)) + 1
                    );

                    orderDto.setUser(userService.readById(userId));
                    orderDto.setRooms(rooms);
                    orderDto.setAmount(amount);
                    orderDto.setOrderDate(now);

                    Order order = orderService.create(orderDto);

                    model.addAttribute("order", OrderUpdateMapper.mapToDto(order));

                    return "order-info";
                }
            }
        }


        if ((dayIn.isBlank() && !dayOut.isBlank()) || (!dayIn.isBlank() && dayOut.isBlank())) {
            model.addAttribute("message", "Incorrect dates");
        }

        model.addAttribute("order", orderDto);
        model.addAttribute("countries", hotelService.getAllCountries());
        model.addAttribute("hotels", hotelService.getAllHotels());
        model.addAttribute("user_id", userId);

        return "create-order";
    }


    @GetMapping("/{order_id}/read/users/{user_id}")
    public String read(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId, Model model) {
        userService.readById(userId);
        Order order = orderService.readById(orderId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("formatter", formatter);
        model.addAttribute("order", OrderUpdateMapper.mapToDto(order));
        model.addAttribute("rooms", order.getReservedRooms().stream()
                .map(r -> r.getNumber().toString() + "   ")
                .reduce("", String::concat));
        return "order-info";
    }


    @GetMapping("/all/users/{user_id}")
    public String getAllByUserId(@PathVariable("user_id") long userId, Model model) {
        List<Order> orders = orderService.readByUserId(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("formatter", formatter);
        model.addAttribute("orders", orders);
        model.addAttribute("user", userService.readById(userId));
        return "orders-user";
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        List<Order> orders = orderService.getAllOrders();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("formatter", formatter);
        model.addAttribute("orders", orders);
        return "orders-all";
    }


    @GetMapping("/{order_id}/update/users/{user_id}")
    public String update(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId, Model model) {
        Order order = orderService.readById(orderId);
        model.addAttribute("order", OrderUpdateMapper.mapToDto(order));
        return "update-order";
    }


    @PostMapping("/{order_id}/update/users/{user_id}")
    public String update(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId,
                         @Validated @ModelAttribute("orderDto") OrderUpdateDto orderDto, BindingResult result) {
        if (result.hasErrors()) {
            orderDto.setUser(userService.readById(userId));
            return "update-order";
        }
        orderService.update(orderDto);
        return "redirect:/orders/all/users/" + userId;
    }


    @GetMapping("/{order_id}/delete/users/{user_id}")
    public String delete(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId) {
        Order order = orderService.readById(orderId);
        List<Room> rooms = order.getReservedRooms();

        for (Room room : rooms) {
            room.getOrders().remove(order);
            roomService.update(room);
        }
        orderService.delete(orderId);
        return "redirect:/orders/all/users/" + userId;
    }


    public static int intervalDays(LocalDate in, LocalDate out) {
        return (int) in.until(out, ChronoUnit.DAYS);
    }


    public static Boolean isValidDates(String in, String out) {
        if (in.isBlank() || out.isBlank()) return false;
        System.out.println("intervalDays " + in + ", " + out + ": " + intervalDays(LocalDate.parse(in), LocalDate.parse(out)));
        return intervalDays(LocalDate.parse(in), LocalDate.parse(out)) >= 0;
    }


    public static  List<Order> getOrdersByHotelAtDates(List<Order> orders,
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
