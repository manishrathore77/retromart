package com.thinking.machines.retro.dao;

import com.thinking.machines.retro.modal.*;
import com.thinking.machines.retro.utility.RetroConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /*------------------------------------------------------------
     * Insert a new user and return the generated user_id
     *-----------------------------------------------------------*/
    public int addUser(User user) throws SQLException {
        String sql = "INSERT INTO users " +
                     "(name, email, phone, password, user_type) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = RetroConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getUserType());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;   // insert failed
    }

    /*------------------------------------------------------------
     * Fetch one user by primary key
     *-----------------------------------------------------------*/
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = RetroConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /*------------------------------------------------------------
     * Fetch one user by email (used for login / uniqueness checks)
     *-----------------------------------------------------------*/
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = RetroConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /*------------------------------------------------------------
     * Return all users (buyers, sellers, admins)
     *-----------------------------------------------------------*/
    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id";
        try (Connection conn = RetroConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    /*------------------------------------------------------------
     * Update an existing user. Returns true if exactly one row changed.
     *-----------------------------------------------------------*/
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name=?, email=?, phone=?, " +
                     "password=?, user_type=? WHERE user_id=?";
        try (Connection conn = RetroConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getUserType());
            ps.setInt   (6, user.getUserId());
            return ps.executeUpdate() == 1;
        }
    }

    /*------------------------------------------------------------
     * Delete a user by id. Returns true if a row was removed.
     *-----------------------------------------------------------*/
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = RetroConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() == 1;
        }
    }

    /*------------------------------------------------------------
     * Validate login; returns User on success, null on failure.
     *-----------------------------------------------------------*/
    public User validateLogin(String email, String password)
            throws SQLException {
        String sql = "SELECT * FROM users " +
                     "WHERE email = ? AND password = ?";
        try (Connection conn = RetroConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);  // store hashed pwd in real app
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /*------------------------------------------------------------
     * Helper: convert ResultSet row to User object
     *-----------------------------------------------------------*/
    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setPassword(rs.getString("password"));
        u.setUserType(rs.getString("user_type"));
        return u;
    }
}