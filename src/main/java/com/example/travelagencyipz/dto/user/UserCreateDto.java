package com.example.travelagencyipz.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.travelagencyipz.model.Role;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreateDto userDto = (UserCreateDto) o;
        return email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}