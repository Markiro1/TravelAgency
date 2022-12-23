package org.project.travelagency.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToMany
    @JoinTable(name = "order_rooms",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
}