package com.example.travelagencyipz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 30, message = "Invalid hotel name")
    @Column(name = "name")
    private String name;

    @Column(name = "country", nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Pattern(regexp = "", message = "Invalid city name")
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel")
    private List<Order> orders;

    public Hotel(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}