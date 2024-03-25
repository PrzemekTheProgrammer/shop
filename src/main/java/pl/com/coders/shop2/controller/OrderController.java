package pl.com.coders.shop2.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.com.coders.shop2.dto.OrderDTO;
import pl.com.coders.shop2.service.OrderService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDTO createOrderByEmail(@RequestParam String userEmail) {
        return orderService.createOrderByEmail(userEmail);
    }

    @GetMapping
    public List<OrderDTO> getOrdersByEmail(@RequestParam String userEmail) {
        return orderService.getOrdersByEmail(userEmail);
    }

}
