package org.project.travelagency.dto.order;

import lombok.*;
import org.project.travelagency.model.Hotel;
import org.project.travelagency.model.Room;
import org.project.travelagency.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;

    private LocalDateTime orderDate;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private User user;

    private Hotel hotel;

    private List<Room> rooms;
}
