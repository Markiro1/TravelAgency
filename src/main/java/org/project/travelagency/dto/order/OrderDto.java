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

    @NotNull
    private LocalDateTime orderDate;

    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;

    @NotNull
    private User user;

    @NotNull
    private Hotel hotel;

    @NotNull
    private List<Room> rooms;
}
