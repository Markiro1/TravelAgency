package org.project.travelagency.controller;

import org.project.travelagency.dto.order.OrderDto;
import org.project.travelagency.mapper.OrderMapper;
import org.project.travelagency.model.Order;
import org.project.travelagency.service.OrderService;
import org.project.travelagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/create/{user_id}")
    public String create(@PathVariable("user_id") long userId, Model model) {
        model.addAttribute("order", new OrderDto());
        model.addAttribute("userId", userId);
        return "create-order";
    }

    @PostMapping("/create/{user_id}")
    public String create(@PathVariable("user_id") long userId,
                         @Validated @ModelAttribute("orderDto") OrderDto orderDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "create-order";
        }
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto.setUser(userService.readById(userId));
        orderService.create(orderDto);
        return "order-info";
    }

    @GetMapping("/{order_id}/read/users/{user_id}")
    public String read(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId, Model model) {
        Order order = orderService.readByUserId(userId).stream().filter(o -> o.getId() == orderId).findFirst().get();
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
        return "redirect:/order/all/users/" + userId;
    }

    @GetMapping("/{order_id}/delete/users/{user_id}")
    public String delete(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId) {
        orderService.delete(orderId);
        return "redirect:/orders/all/users/" + userId;
    }
}