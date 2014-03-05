package ru.dictonaryBooks.model.DAO;

import org.hibernate.Session;
import ru.dictonaryBooks.model.domain.Author;
import ru.dictonaryBooks.model.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nasak on 21.02.14.
 */
public class AuthorDAOImpl implements AuthorDAO {
    @Override
    public void create(Author author) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(author);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void update(Author author) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(author);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Author getById(Long id) throws SQLException {
        Session session = null;
        Author author = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            author = (Author) session.load(Author.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return author;
    }

    @Override
    public List getAll() throws SQLException {
        Session session = null;
        List<Author> author = new ArrayList<Author>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            author = session.createCriteria(Author.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return author;
    }

    @Override
    public void delete(Author author) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(author);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Session session = null;
        Author author = null;
        try {
            author = getById(id);
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(author);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
