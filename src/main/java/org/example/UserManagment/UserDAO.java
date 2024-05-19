package org.example.UserManagment;

import org.example.DataBase.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        try {
            connection = DatabaseConnector.connect();
        } catch (SQLException e) {
            System.out.println("Veritabanı bağlantısı oluşturulurken hata oluştu: " + e.getMessage());
        }
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email, password, salt) VALUES (?, ?, ?, ?, ?)";
        String salt = PasswordUtils.getSalt();
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword(), salt);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setString(5, salt);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Kullanıcı eklenirken hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                return new User(id, firstName, lastName, email, password, salt);
            }
        } catch (SQLException e) {
            System.out.println("Kullanıcı getirilirken hata oluştu: " + e.getMessage());
        }
        return null;
    }
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Kullanıcı güncellenirken hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        DatabaseConnector.disconnect(connection);
    }
}
