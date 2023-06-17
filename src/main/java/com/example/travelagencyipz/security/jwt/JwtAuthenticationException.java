package com.example.travelagencyipz.security.jwt;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
    private HttpStatus httpStatus;

    public JwtAuthenticationException(String detail) {
        super(detail);
    }

    public JwtAuthenticationException(String detail, HttpStatus httpStatus) {
        super(detail);
        this.httpStatus = httpStatus;
    }
}
