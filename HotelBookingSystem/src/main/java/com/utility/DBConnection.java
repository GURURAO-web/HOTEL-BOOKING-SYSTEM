package com.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hotelstay";

    private static final String USER = "root";

    private static final String PASSWORD = "gururao123";

    private DBConnection() {
        // Prevent object creation
    }

    public static Connection getDBConnection() throws SQLException {

        System.out.println("Request for DB connection...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        Connection connection =
                DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.println("DB connected successfully!");

        return connection;
    }
}