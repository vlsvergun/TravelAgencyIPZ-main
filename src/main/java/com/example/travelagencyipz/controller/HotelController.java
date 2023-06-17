package com.example.travelagencyipz.controller;

import com.example.travelagencyipz.model.Country;
import com.example.travelagencyipz.model.Hotel;
import com.example.travelagencyipz.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("countries", Country.values());
        return "create-hotel";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("hotel") Hotel hotel,
                         Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "create-hotel";
        }
        Hotel h = hotelService.create(hotel);
        model.addAttribute("hotel", hotelService.readById(h.getId()));
        return "hotel-info";
    }

    @GetMapping("/{hotel_id}/read")
    public String read(@PathVariable("hotel_id") long hotelId, Model model) {
        Hotel hotel = hotelService.readById(hotelId);
        model.addAttribute("hotel", hotel);
        return "hotel-info";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "hotels-list";
    }

    @GetMapping("/{hotel_id}/update")
    public String update(@PathVariable("hotel_id") long hotelId, Model model) {
        Hotel hotel = hotelService.readById(hotelId);
        model.addAttribute("hotel", hotel);
        model.addAttribute("countries", Country.values());
        return "update-hotel";
    }

    @PostMapping("/{hotel_id}/update")
    public String update(@PathVariable("hotel_id") long hotelId,
                         Model model,
                         @Validated @ModelAttribute("hotel") Hotel hotel,
                         BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("hotel", hotelService.readById(hotelId));
            model.addAttribute("countries", Country.values());
            return "update-hotel";
        }
        hotelService.update(hotel);
        return "redirect:/hotels/" + hotelId + "/read";
    }

    @GetMapping("/{hotel_id}/delete")
    public String delete(@PathVariable("hotel_id") long hotelId) {
        hotelService.delete(hotelId);
        return "redirect:/hotels/all";
    }
}