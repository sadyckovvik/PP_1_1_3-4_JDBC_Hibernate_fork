package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // 1. создание таблицы юзеров
        //  2. Добавление 4 User(ов) в таблицу с данными на свой выбор.
        //  После каждого добавления должен быть вывод в консоль (User с именем — name добавлен в базу данных)
        // 3. Получение всех User из базы и вывод в консоль
        // 4. Очистка таблицы User(ов)
        // 5. Удаление таблицы
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vitya", "Sadykov", (byte) 28);
        System.out.printf("User с именем — %s добавлен в базу данных\n", "Vitya");
        userService.saveUser("Vadim", "Sergienko", (byte) 27);
        System.out.printf("User с именем — %s добавлен в базу данных\n", "Vadim");
        userService.saveUser("Pavel", "Alliluev", (byte) 26);
        System.out.printf("User с именем — %s добавлен в базу данных\n", "Pavel");
        userService.saveUser("Eliza", "Telenkova", (byte) 26);
        System.out.printf("User с именем — %s добавлен в базу данных\n", "Eliza");
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
