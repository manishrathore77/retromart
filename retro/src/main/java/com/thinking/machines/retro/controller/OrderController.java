package com.thinking.machines.retro.controller;

import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.modal.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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




@PutMapping("/{id}/confirm")
public ResponseEntity<?> confirmPayment(@PathVariable int id, @RequestBody Order updatedOrder) {
    try {
        boolean ok = orderDAO.confirmPayment(id, updatedOrder.getTransactionId(), updatedOrder.getPaymentStatus());
        return ok ? ResponseEntity.ok("Payment confirmed")
                  : ResponseEntity.status(404).body("Order not found");
    } catch (SQLException e) {
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}

@GetMapping
public ResponseEntity<List<Order>> getAllOrders() {
    try {
        List<Order> orders = orderDAO.getAllOrders();
        return ResponseEntity.ok(orders);
    } catch (SQLException e) {
        return ResponseEntity.status(500).build();
    }
}

    @PutMapping("/payment/{orderId}")
    public ResponseEntity<?> updatePayment(@PathVariable int orderId,
                                           @RequestParam String status,
                                           @RequestParam String txnId) {
        try {
            boolean ok = orderDAO.updatePaymentStatus(orderId, status, txnId);
            return ok ? ResponseEntity.ok("Payment status updated")
                      : ResponseEntity.status(404).body("Order not found");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/delivery/{orderId}")
    public ResponseEntity<?> updateDelivery(@PathVariable int orderId,
                                            @RequestParam String status) {
        try {
            boolean ok = orderDAO.updateDeliveryStatus(orderId, status);
            return ok ? ResponseEntity.ok("Delivery status updated")
                      : ResponseEntity.status(404).body("Order not found");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
