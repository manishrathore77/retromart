package com.thinking.machines.retro.controller;

import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.modal.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDAO userDAO = new UserDAO();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            int id = userDAO.addUser(user);
            return ResponseEntity.ok("User created with id " + id);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
    try {
        User existing = userDAO.validateLogin(user.getEmail(), user.getPassword());
        if (existing != null) {
            session.setAttribute("currentUser", existing); // ✅ store user in session
            return ResponseEntity.ok(existing);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    } catch (SQLException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}


    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable int id) {
        try {
            User u = userDAO.getUserById(id);
            return (u != null) ? ResponseEntity.ok(u)
                               : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        try {
            return ResponseEntity.ok(userDAO.getAllUsers());
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody User user) {
        try {
           // user.userId = id; 
          user.setUserId(id); 
            boolean ok = userDAO.updateUser(user);
            return ok ? ResponseEntity.ok("Updated")
                      : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            boolean ok = userDAO.deleteUser(id);
            return ok ? ResponseEntity.ok("Deleted")
                      : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
@PostMapping("/logout")
public ResponseEntity<String> logout(HttpSession session) {
    session.invalidate(); // clear session
    return ResponseEntity.ok("Logged out successfully");
}

@GetMapping("/session")
public ResponseEntity<?> getSessionUser(HttpSession session) {
    User user = (User) session.getAttribute("currentUser");
    if (user != null) return ResponseEntity.ok(user);
    return ResponseEntity.status(401).body("No active session");
}


}
