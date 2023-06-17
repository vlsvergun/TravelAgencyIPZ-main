package com.example.travelagencyipz.controller;

import com.example.travelagencyipz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String booking(Principal principal, Model model) {
        var u = userService.getUserByEmail(principal.getName());

        model.addAttribute("id",
                String.valueOf(userService.getUserByEmail(principal.getName()).getId()));

        model.addAttribute("user",
                userService.getUserByEmail(principal.getName()).getUserName());

        if (u.getRole().name().equals("USER")) {
            return "home";
        } else return "home-admin";
    }

    @GetMapping("/denied")
    public String accessDenied() {
        return "error";
    }
}