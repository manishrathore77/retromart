package com.thinking.machines.retro.controller;

import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.modal.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

private final ProductDAO productDAO = new ProductDAO();

  @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product p, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !"seller".equalsIgnoreCase(current.getUserType()))
            return ResponseEntity.status(401).body("Not logged\u2011in seller");

        p.setSellerId(current.getUserId());
        try {
            int id = productDAO.addProduct(p);
            return ResponseEntity.ok("{\"productId\":" + id + "}");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

@GetMapping
public ResponseEntity<List<Product>> all() {
try {
return ResponseEntity.ok(productDAO.getAllProducts());
} catch (SQLException e) {
return ResponseEntity.status(500).build();
}
}

@GetMapping("/{id}")
public ResponseEntity<Product> get(@PathVariable int id) {
try {
Product p = productDAO.getProductById(id);
return (p != null) ? ResponseEntity.ok(p)
: ResponseEntity.notFound().build();
} catch (SQLException e) {
return ResponseEntity.status(500).build();
}
}
}
