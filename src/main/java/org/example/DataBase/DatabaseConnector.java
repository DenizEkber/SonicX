package org.example.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/musicdata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "#123456789#";

    public static Connection connect() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Veritabanına bağlandı!");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC sürücüsü bulunamadı: " + e.getMessage());
        }
        return connection;
    }

    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            } catch (SQLException e) {
                System.out.println("Veritabanı bağlantısı kapatılırken hata oluştu: " + e.getMessage());
            }
        }
    }
}