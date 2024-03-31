package org.example.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties properties = new Properties();
                InputStream inputStream = ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties");
                properties.load(inputStream);

                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                connection = DriverManager.getConnection(url, username, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
