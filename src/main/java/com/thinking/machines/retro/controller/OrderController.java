package com.thinking.machines.retro.controller;

import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.modal.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderDAO orderDAO = new OrderDAO();

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        try {
            int id = orderDAO.addOrder(order);
            return ResponseEntity.ok("Order placed with id " + id);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<?> getOrdersByBuyer(@PathVariable int buyerId) {
        try {
            List<Order> orders = orderDAO.getOrdersByBuyer(buyerId);
            return ResponseEntity.ok(orders);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
