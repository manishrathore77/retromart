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

  @PostMapping                     //   POST /api/products
    public ResponseEntity<?> addProduct(@RequestBody Product p, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");     //  ✅ session check
        if (current == null || !"seller".equalsIgnoreCase(current.getUserType()))
            return ResponseEntity.status(401).body("Not logged‑in seller");

        p.setSellerId(current.getUserId());                                   //   set sellerId
        try {
            int id = productDAO.addProduct(p);
            return ResponseEntity.ok("{\"productId\":" + id + "}");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }
@GetMapping
public ResponseEntity<List<Product>> all(@RequestParam(required = false) String category) {
try {
if (category != null && !category.isBlank())
return ResponseEntity.ok(productDAO.getProductsByCategory(category));
return ResponseEntity.ok(productDAO.getAllProducts());
} catch (SQLException e) {
return ResponseEntity.status(500).build();
}
}

@GetMapping("/search")
public ResponseEntity<List<Product>> search(@RequestParam String keyword) {
try {
return ResponseEntity.ok(productDAO.searchProducts(keyword));
} catch (SQLException e) {
return ResponseEntity.status(500).build();
}
}

@GetMapping("/seller/{sellerId}")
public ResponseEntity<List<Product>> bySeller(@PathVariable int sellerId) {
try {
return ResponseEntity.ok(productDAO.getProductsBySeller(sellerId));
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

@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable int id, HttpSession session) {
try {
User current = (User) session.getAttribute("currentUser");
if (current == null || !"seller".equalsIgnoreCase(current.getUserType()))
return ResponseEntity.status(401).body("Not logged-in seller");

Product product = productDAO.getProductById(id);
if (product == null) return ResponseEntity.notFound().build();
if (product.getSellerId() != current.getUserId())
return ResponseEntity.status(403).body("You can only delete your own products");

boolean ok = productDAO.deleteProduct(id);
return ok ? ResponseEntity.ok("Deleted")
: ResponseEntity.notFound().build();
} catch (SQLException e) {
return ResponseEntity.status(500).body(e.getMessage());
}
}
}