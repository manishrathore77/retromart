package com.thinking.machines.retro.controller;

import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.modal.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ProductImageController {

    private final ProductImageDAO imageDAO = new ProductImageDAO();

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProductImage img) {
        try {
            int id = imageDAO.addImage(img);
            return ResponseEntity.ok("Image id " + id);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductImage>> byProduct(@PathVariable int productId) {
        try {
            return ResponseEntity.ok(imageDAO.getImagesByProductId(productId));
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable int id) {
        try {
            boolean ok = imageDAO.deleteImageById(id);
            return ok ? ResponseEntity.ok("Deleted")
                      : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteByProduct(@PathVariable int productId) {
        try {
            boolean ok = imageDAO.deleteImagesByProductId(productId);
            return ok ? ResponseEntity.ok("Deleted")
                      : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
