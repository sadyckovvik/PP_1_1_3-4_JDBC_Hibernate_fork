package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTable(); // создание таблицы юзеров

    void dropUsersTable(); // удаление таблицы юзеров

    void saveUser(String name, String lastName, byte age); // добавление юзера в таблицу

    void removeUserById(long id); // удаление юзера из таблицы по айди

    List<User> getAllUsers(); //чтение всех юзеров

    void cleanUsersTable(); // очистка содержания таблицы
}
