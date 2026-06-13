package com.thinking.machines.retro.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RetroConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("MySQL Driver not found", ex);
        }

        // A full JDBC URL override takes precedence if provided.
        String jdbcUrl = env("JDBC_URL", "JDBC_DATABASE_URL", "SPRING_DATASOURCE_URL");

        // Otherwise assemble the URL from individual parts. Railway's MySQL
        // plugin exposes MYSQLHOST/MYSQLPORT/etc.; generic DB_* names are also
        // supported. Falls back to localhost so local development still works.
        String host = env("DB_HOST", "MYSQLHOST", "localhost");
        String port = env("DB_PORT", "MYSQLPORT", "3306");
        String name = env("DB_NAME", "MYSQLDATABASE", "retrodb");

        USER     = env("DB_USER", "MYSQLUSER", "retrouser");
        PASSWORD = env("DB_PASSWORD", "MYSQLPASSWORD", "retrouser");

        if (jdbcUrl != null && !jdbcUrl.isBlank()) {
            URL = jdbcUrl;
        } else {
            URL = "jdbc:mysql://" + host + ":" + port + "/" + name
                    + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        }
    }

    /**
     * Returns the first non-blank value among the given environment variable
     * names, or the final argument as a default.
     */
    private static String env(String... namesThenDefault) {
        for (int i = 0; i < namesThenDefault.length - 1; i++) {
            String value = System.getenv(namesThenDefault[i]);
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return namesThenDefault[namesThenDefault.length - 1];
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
