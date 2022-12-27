package org.project.travelagency.controller;

import org.project.travelagency.model.Hotel;
import org.project.travelagency.model.Room;
import org.project.travelagency.service.HotelService;
import org.project.travelagency.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final HotelService hotelService;
    private final RoomService roomService;

    @Autowired
    public RoomController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Hotel> list = hotelService.getAllHotels();
        List<String> hotelNames = list.stream().map(Hotel::getName).toList();
        model.addAttribute("room", new Room());
        model.addAttribute("hotelNames", hotelNames);
        return "create-room";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @RequestParam String hotelName,
                         @RequestParam Integer number,
                         @RequestParam Double price) {
        Hotel hotel = hotelService.getHotelByName(hotelName);

        Room room = Room.builder()
                .number(number)
                .price(price)
                .hotel(hotel)
                .build();
        roomService.create(room);
        model.addAttribute("room", room);
        return "room-info";
    }

    @GetMapping("/{room_id}/read")
    public String read(@PathVariable("room_id") long roomId, Model model) {
        Room room = roomService.readById(roomId);
        model.addAttribute("room", room);
        return "hotel-info";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms-list";
    }

    @GetMapping("/{room_id}/delete")
    public String delete(@PathVariable("room_id") long roomId) {
        roomService.delete(roomId);
        return "redirect:/rooms/all";
    }
}