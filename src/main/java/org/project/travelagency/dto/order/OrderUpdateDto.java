package org.project.travelagency.dto.order;

import lombok.*;
import org.project.travelagency.model.Hotel;
import org.project.travelagency.model.Room;
import org.project.travelagency.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderUpdateDto {
    private long id;
    private LocalDateTime orderDate;
    private String checkIn;
    private String checkOut;
    private Hotel hotel;
    private User user;
    private List<Room> rooms = new ArrayList<>();
    private double amount;
}