package com.thinking.machines.retro.controller;

import com.thinking.machines.retro.dao.*;
import com.thinking.machines.retro.modal.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

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
}
