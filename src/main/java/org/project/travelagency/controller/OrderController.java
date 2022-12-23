//package org.project.travelagency.controller;
//
//import org.project.travelagency.model.Order;
//import org.project.travelagency.service.OrderService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Controller
//@RequestMapping("/orders")
//public class OrderController {
//
//    private final UserService userService;
//    private final OrderService orderService;
//
//
//    public OrderController(UserService userService, OrderService orderService) {
//        this.userService = userService;
//        this.orderService = orderService;
//    }
//
//
//    @GetMapping("/{order_id}/read/users/{user_id}")
//    public String read(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId, Model model) {
//        Order order = orderService.getByUserId(userId).stream().filter(o -> o.getId()==orderId).findFirst().get();
//        model.addAttribute("order", order);
//        model.addAttribute("userId", userId);
//        return "order-info";
//    }
//
//    @GetMapping("/create/orders/{user_id}")
//    public String create(@PathVariable("user_id") long userId, Model model) {
//        model.addAttribute("order", new Order());
//        model.addAttribute("userId", userId);
//        return "create-order";
//    }
//
//    @PostMapping("/create/orders/{user_id}")
//    public String create(@PathVariable("user_id") long userId,
//                         @Validated @ModelAttribute("order") Order order,
//                         BindingResult result) {
//        if (result.hasErrors()) {
//            return "create-order";
//        }
//        order.setOrderDate(LocalDateTime.now());
//        order.setUser(userService.readById(userId));
//        orderService.create(order);
//        return "order-info";
//    }
//
//    @GetMapping("/{order_id}/update/users/{user_id}")
//    public String update(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId, Model model) {
//        Order order = orderService.readById(orderId);
//        model.addAttribute("order", order);
//        return "update-order";
//    }
//
//    @PostMapping("/{order_id}/update/users/{user_id}")
//    public String update(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId,
//                         @Validated @ModelAttribute("order") Order order, BindingResult result) {
//        if (result.hasErrors()) {
//            order.setUser(userService.readById(userId));
//            return "update-order";
//        }
//        orderService.update(order);
//        return "redirect:/order/all/users/" + userId;
//    }
//
//    @GetMapping("/{order_id}/delete/users/{user_id}")
//    public String delete(@PathVariable("order_id") long orderId, @PathVariable("user_id") long userId) {
//        orderService.delete(orderId);
//        return "redirect:/orders/all/users/" + userId;
//    }
//
//    @GetMapping("/all/users/{user_id}")
//    public String getAll(@PathVariable("user_id") long userId, Model model) {
//        List<Order> orders = orderService.getByUserId(userId);
//        model.addAttribute("orders", orders);
//        model.addAttribute("user", userService.readById(userId));
//        return "oders-user";
//    }
//}
