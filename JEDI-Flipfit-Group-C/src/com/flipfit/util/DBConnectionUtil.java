package com.flipfit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 */
public class DBConnectionUtil {

    // Replace with your actual database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/flipfit_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "tarunteja";


    /**
     * Establishes and returns a connection to the database.
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Please add it to your classpath.");
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}