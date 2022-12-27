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
public class OrderCreateDto {
    private Long id;

    private LocalDateTime orderDate;

    private String checkIn;

    private String checkOut;

    private String country;

    private String hotel;

    private User user;

    private List<Room> rooms = new ArrayList<>();

    private int reservedRoomsCount;

    private double amount;
}
