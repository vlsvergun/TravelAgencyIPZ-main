package com.example.travelagencyipz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

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
        if (!(o instanceof Room room)) return false;
        return Objects.equals(number, room.number) && Objects.equals(hotel, room.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, hotel);
    }
}