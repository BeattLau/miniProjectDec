package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements AutoCloseable {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/BookStore";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "LBsabbath89!";
    private Connection connection;

    private DBConnection(Connection connection) {
        this.connection = connection;
    }

    public static DBConnection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        return new DBConnection(connection);
    }


    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
