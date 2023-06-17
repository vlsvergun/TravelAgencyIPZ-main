package com.example.travelagencyipz.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String email;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLoginDto userDto = (UserLoginDto) o;
        return email.equals(userDto.email) && password.equals(userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}