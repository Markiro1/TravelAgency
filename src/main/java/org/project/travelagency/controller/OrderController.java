package org.project.travelagency.controller;

import org.project.travelagency.dto.order.OrderCreateDto;
import org.project.travelagency.dto.order.OrderDto;
import org.project.travelagency.mapper.OrderMapper;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

        System.out.println("in 0");

        if (result.hasErrors()) {
            return "create-order";
        }

        String dayIn = orderDto.getCheckIn();
        String dayOut = orderDto.getCheckOut();
        LocalDateTime now = LocalDateTime.now();

        if (orderDto.getCountry() != null && isValidDates(now.toLocalDate().toString(), dayIn) & isValidDates(dayIn, dayOut)){

            List<Order> orders = orderService.getAllOrders().stream()
                    .filter(order -> order.getHotel().getName().equals(orderDto.getHotel()))
                    .filter(order -> !isValidDates(dayOut, order.getCheckIn().minusDays(1L).toString())
            || !isValidDates(order.getCheckOut().plusDays(1L).toString(), dayIn))
                    .toList();

            List<Room> reservedRooms = orders.stream()
                    .map(Order::getReservedRooms)
                    .flatMap(List::stream)
                    .distinct()
                    .toList();

            List<Room> freeRooms = roomService.getRoomsByHotelId(hotelService.getHotelByName(orderDto.getHotel()).getId())
                    .stream()
                    .filter(r -> !reservedRooms.contains(r))
                    .toList();

            model.addAttribute("freeRoomsNumber", IntStream.range(1, freeRooms.size() + 1).toArray());
            model.addAttribute("price", freeRooms.get(0).getPrice());

            int roomsCount = orderDto.getReservedRoomsCount();

            if (roomsCount != 0) {

                List<Room> rooms = freeRooms.stream().limit(roomsCount).toList();

                double amount = roomsCount * rooms.get(0).getPrice() * (intervalDays(LocalDate.parse(dayIn), LocalDate.parse(dayOut)) + 1);

                orderDto.setUser(userService.readById(userId));
                orderDto.setRooms(rooms);
                orderDto.setAmount(amount);
                orderDto.setOrderDate(now);

                Order order = orderService.create(orderDto);
                userService.readById(userId).getOrders().add(order);
                hotelService.getHotelByName(orderDto.getHotel()).getOrders().add(order);

                model.addAttribute("order", orderDto);

                return "order-info";
            }
        }

        //model.addAttribute("message", "Incorrect dates");

        model.addAttribute("order", orderDto);
        model.addAttribute("countries", hotelService.getAllCountries());
        model.addAttribute("hotels", hotelService.getAllHotels());
        model.addAttribute("user_id", userId);

        return "create-order";
    }


    @GetMapping("/{order_id}/read/users/{user_id}")
    public String read(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId, Model model) {
        Order order = orderService.readByUserId(userId).stream().filter(o -> o.getId()==orderId).findFirst().get();
        model.addAttribute("order", OrderMapper.mapToDto(order));
        model.addAttribute("userId", userId);
        return "order-info";
    }


    @GetMapping("/all/users/{user_id}")
    public String getAllByUserId(@PathVariable("user_id") long userId, Model model) {
        List<Order> orders = orderService.readByUserId(userId);
        model.addAttribute("orders", orders);
        model.addAttribute("user", userService.readById(userId));
        return "orders-user";
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders-all";
    }


    @GetMapping("/{order_id}/update/users/{user_id}")
    public String update(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId, Model model) {
        Order order = orderService.readById(orderId);
        model.addAttribute("order", OrderMapper.mapToDto(order));
        return "update-order";
    }


    @PostMapping("/{order_id}/update/users/{user_id}")
    public String update(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId,
                         @Validated @ModelAttribute("orderDto") OrderDto orderDto, BindingResult result) {
        if (result.hasErrors()) {
            orderDto.setUser(userService.readById(userId));
            return "update-order";
        }
        orderService.update(orderDto);
        return "redirect:/orders/all/users/" + userId;
    }


    @GetMapping("/{order_id}/delete/users/{user_id}")
    public String delete(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId) {
        orderService.delete(orderId);
        return "redirect:/orders/all/users/" + userId;
    }


    public static int intervalDays(LocalDate in, LocalDate out) {
        return (int) in.until(out, ChronoUnit.DAYS);
    }


    public static Boolean isValidDates(String in, String out) {
        if (in.isBlank() || out.isBlank()) return false;
        return intervalDays(LocalDate.parse(in), LocalDate.parse(out)) >= 0;
    }

}
