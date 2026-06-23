package com.tracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage MySQL JDBC connections.
 * Works both locally and on Railway.
 * - Local: reads from hardcoded values below
 * - Railway: reads from environment variables automatically
 */
public class DBConnection {

    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASS;

    static {
        // Railway injects these env vars automatically when MySQL plugin is added
        String host     = System.getenv("MYSQLHOST");
        String port     = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user     = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        if (host != null && !host.isEmpty()) {
            // Running on Railway
            DB_URL  = "jdbc:mysql://" + host + ":" + port + "/" + database
                    + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            DB_USER = user;
            DB_PASS = password;
        } else {
            // Running locally
            DB_URL  = "jdbc:mysql://localhost:3306/warehouse_tracker?useSSL=false&serverTimezone=UTC";
            DB_USER = "root";   // change to your local MySQL username
            DB_PASS = "root";   // change to your local MySQL password
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
