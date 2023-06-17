package com.example.travelagencyipz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "[A-Z][a-z]+", message = "Must start with a capital letter")
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+", message = "Must start with a capital letter")
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Email(message = "Invalid email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    public String getUserName() {
        return lastName + " " + firstName;
    }
}