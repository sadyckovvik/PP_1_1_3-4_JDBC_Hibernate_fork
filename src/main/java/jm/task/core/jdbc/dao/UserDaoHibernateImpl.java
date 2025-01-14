package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    private final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user (Id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(60), LastName VARCHAR(60), Age TINYINT NOT NULL)").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при создании таблицы пользователей", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы пользователей", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при сохранении пользователя", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при получении пользователей", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Ошибка при очистке таблицы пользователей", e);
        }
    }
}
