package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {

    private final Connection con;

    public ConnectionSQL() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/kopidlnodb";
        String username = "admin";
        String password = "qqww";
        con = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return con;
    }
}