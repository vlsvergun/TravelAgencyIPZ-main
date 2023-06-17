package com.example.travelagencyipz.controller;

import com.example.travelagencyipz.dto.order.OrderCreateDto;
import com.example.travelagencyipz.dto.order.OrderUpdateDto;
import com.example.travelagencyipz.mapper.OrderUpdateMapper;
import com.example.travelagencyipz.model.Order;
import com.example.travelagencyipz.model.Room;
import com.example.travelagencyipz.security.SecurityUser;
import com.example.travelagencyipz.service.HotelService;
import com.example.travelagencyipz.service.OrderService;
import com.example.travelagencyipz.service.RoomService;
import com.example.travelagencyipz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final HotelService hotelService;
    private final RoomService roomService;

    @Autowired
    public OrderController(UserService userService,
                           OrderService orderService,
                           HotelService hotelService,
                           RoomService roomService) {
        this.userService = userService;
        this.orderService = orderService;
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @GetMapping("/create/{user_id}")
    public String create(@PathVariable("user_id") long userId, Model model) {

        OrderCreateDto orderDto = new OrderCreateDto();
        orderDto.setUser(userService.readById(userId));
        model.addAttribute("order", orderDto);
        model.addAttribute("countries", hotelService.getAllCountries());
        return "create-order";
    }

    @PostMapping("/create/{user_id}")
    public String create(@PathVariable("user_id") long userId,
                         @Validated @ModelAttribute("orderDto") OrderCreateDto orderDto,
                         Model model,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "create-order";
        }

        String dayIn = orderDto.getCheckIn();
        String dayOut = orderDto.getCheckOut();
        LocalDateTime now = LocalDateTime.now();

        if (orderDto.getCountry() != null
                && orderService.isValidDates(now.toLocalDate().toString(), dayIn)
                && orderService.isValidDates(dayIn, dayOut)) {

            if (orderDto.getHotel() != null) {

                List<Order> ordersByHotelAtDates = orderService
                        .getOrdersByHotelAtDates(orderService.getAllOrders(), orderDto, dayIn, dayOut);

                List<Room> reservedRoomsByHotelAtDates = orderService
                        .getReservedRoomsByHotelAtDates(ordersByHotelAtDates);

                List<Room> allRoomsByHotel = roomService
                        .getRoomsByHotelId(hotelService.getHotelByName(orderDto.getHotel()).getId());

                List<Room> freeRoomsByHotel = orderService
                        .getFreeRoomsByHotelAtDates(reservedRoomsByHotelAtDates, allRoomsByHotel);

                if (freeRoomsByHotel != null) {
                    model.addAttribute("freeRoomsNumber",
                            IntStream.range(1, freeRoomsByHotel.size() + 1).toArray());

                    model.addAttribute("price", freeRoomsByHotel.get(0).getPrice());
                    model.addAttribute("turnOff", true);
                } else {
                    model.addAttribute("freeRoomsNumber", new ArrayList<>());
                    model.addAttribute("price", null);
                    model.addAttribute("message", "There are no available rooms");
                }
                int reservedRoomsCount = orderDto.getReservedRoomsCount();

                if (reservedRoomsCount > 0) {

                    List<Room> rooms = freeRoomsByHotel.stream().limit(reservedRoomsCount).toList();

                    double amount = reservedRoomsCount * rooms.get(0).getPrice() * (
                            orderService.intervalDays(LocalDate.parse(dayIn), LocalDate.parse(dayOut)) + 1
                    );
                    orderDto.setUser(userService.readById(userId));
                    orderDto.setRooms(rooms);
                    orderDto.setAmount(amount);
                    orderDto.setOrderDate(now);
                    Order order = orderService.create(orderDto);

                    return "redirect:/orders/" + order.getId() + "/read/users/" + order.getUser().getId();
                }
            }
        }

        if ((dayIn.isBlank() && !dayOut.isBlank()) || (!dayIn.isBlank() && dayOut.isBlank())
                || ((!dayIn.isBlank() && !dayOut.isBlank()) && !orderService.isValidDates(dayIn, dayOut))
                || ((!dayIn.isBlank() && !dayOut.isBlank()) && !orderService.isValidDates(now.toLocalDate().toString(), dayIn))) {
            model.addAttribute("message", "Incorrect dates");
        }

        model.addAttribute("order", orderDto);
        model.addAttribute("countries", hotelService.getAllCountries());
        model.addAttribute("hotels", hotelService.getAllHotels());
        model.addAttribute("user_id", userId);

        return "create-order";
    }

    @GetMapping("/{order_id}/read/users/{user_id}")
    public String read(@PathVariable("order_id") long orderId,
                       @PathVariable("user_id") long userId,
                       Model model) {

        userService.readById(userId);
        Order order = orderService.readById(orderId);
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("order", OrderUpdateMapper.mapToDto(order));
        model.addAttribute("rooms", order.getReservedRooms().stream()
                .map(r -> r.getNumber().toString() + "   ")
                .reduce("", String::concat));
        return "order-info";
    }

    @GetMapping("/all/users/{user_id}")
    public String getAllByUserId(@PathVariable("user_id") long userId, Model model) {
        List<Order> orders = orderService.readByUserId(userId);
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("orders", orders);
        model.addAttribute("user", userService.readById(userId));
        return "orders-user";
    }

    @GetMapping("/all/users")
    public String getAllByUser(Authentication authentication) {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        System.out.println(user.getId());
        return "redirect:/orders/all/users/" + user.getId();
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("orders", orders);
        return "orders-all";
    }

    @GetMapping("/{order_id}/update/users/{user_id}")
    public String update(@PathVariable("order_id") long orderId,
                         @PathVariable("user_id") long userId,
                         Model model) {

        Order order = orderService.readById(orderId);
        model.addAttribute("order", OrderUpdateMapper.mapToDto(order));
        return "update-order";
    }

    @PostMapping("/{order_id}/update/users/{user_id}")
    public String update(@PathVariable("order_id") long orderId,
                         @PathVariable("user_id") long userId,
                         @Validated @ModelAttribute("orderDto") OrderUpdateDto orderDto,
                         BindingResult result) {

        if (result.hasErrors()) {
            orderDto.setUser(userService.readById(userId));
            return "update-order";
        }
        orderService.update(orderDto);
        return "redirect:/orders/all/users/" + userId;
    }

    @GetMapping("/{order_id}/delete/users/{user_id}")
    public String delete(@PathVariable("order_id") long orderId,
                         @PathVariable("user_id") long userId) {

        Order order = orderService.readById(orderId);
        List<Room> rooms = order.getReservedRooms();

        for (Room room : rooms) {
            room.getOrders().remove(order);
            roomService.update(room);
        }
        orderService.delete(orderId);
        return "redirect:/orders/all";
    }
}