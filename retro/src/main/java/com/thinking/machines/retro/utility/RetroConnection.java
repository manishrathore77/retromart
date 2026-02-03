package com.thinking.machines.retro.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RetroConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/retrodb?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "retrouser";
    private static final String PASSWORD = "retrouser";

    // optional: static block to load driver (modern MySQL driver auto‑loads)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("MySQL Driver not found", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
