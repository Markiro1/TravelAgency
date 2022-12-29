package org.project.travelagency.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    @Min(value = 1, message = "Room number can not be 0")
    @Max(value = 500, message = "Max allowed room number is 500")
    private Integer number;

    @Column(name = "price")
    @Min(value = 1, message = "Price can not be 0")
    @Max(value = 5000, message = "Max allowed price is 5000$")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "reservedRooms")
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}