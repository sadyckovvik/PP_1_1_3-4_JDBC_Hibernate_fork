package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

//    public static Connection getConnection() throws SQLException {
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            System.err.println("Не удалось зарегистрировать драйвер: " + e.getMessage());
//        }
//        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
//    }

    private static Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
//это коллекция ключ-значение, которая используется для хранения настроек Hibernate.

//Настройка параметров подключения к базе данных
        settings.put(Environment.DRIVER, DRIVER);
        settings.put(Environment.URL, URL);
        settings.put(Environment.USER, USERNAME);
        settings.put(Environment.PASS, PASSWORD);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        settings.put(Environment.SHOW_SQL, "true");
//Hibernate будет выводить SQL-запросы в консоль
//        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//Значение "thread" означает, что сессия будет привязана к текущему потоку (thread)
        settings.put(Environment.HBM2DDL_AUTO, "");
//Пустое значение ("") означает, что Hibernate не будет автоматически управлять схемой базы данных.
        configuration.setProperties(settings);
//Применяем настройки из объекта Properties к объекту Configuration
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }

    private static SessionFactory sessionFactory;

    //настройка SessionFactory для работы с Hibernate
    //SessionFactory — это ключевой объект в Hibernate,
    // который используется для создания сессий (Session) для взаимодействия с базой данных
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = createConfiguration();
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
//ServiceRegistry: Это контейнер для сервисов, которые использует Hibernate
//StandardServiceRegistryBuilder: Строит ServiceRegistry на основе настроек, указанных в Configuration
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//buildSessionFactory: Создает объект SessionFactory на основе настроек и зарегистрированных сущностей.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
