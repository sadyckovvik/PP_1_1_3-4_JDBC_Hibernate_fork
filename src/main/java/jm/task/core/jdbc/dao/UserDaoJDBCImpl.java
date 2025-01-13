package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(60), LastName VARCHAR(60), Age INT)";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.log(Level.SEVERE, "Ошибка при выполнении SQL-запроса", e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при закрытии соединения", e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.log(Level.SEVERE, "Ошибка при выполнении SQL-запроса", e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при работе с БД", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name,lastName,age) VALUES(?,?,?)";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.log(Level.SEVERE, "Ошибка при выполнении SQL-запроса", e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при работе с БД", e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.log(Level.SEVERE, "Ошибка при выполнении SQL-запроса", e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при работе с БД", e);
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    users.add(user);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.log(Level.SEVERE, "Ошибка при выполнении SQL-запроса", e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при работе с БД", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                connection.rollback();
                logger.log(Level.SEVERE, "Ошибка при выполнении SQL-запроса", e);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при работе с БД", e);
        }
    }
}
