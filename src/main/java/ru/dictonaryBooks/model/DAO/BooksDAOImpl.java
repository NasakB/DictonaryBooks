package ru.dictonaryBooks.model.DAO;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.dictonaryBooks.model.domain.Books;
import ru.dictonaryBooks.model.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nasak on 23.02.14.
 */
public class BooksDAOImpl implements BooksDAO {
    @Override
    public void create(Books book) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(book);
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
    public void update(Books book) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(book);
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
    public Books getById(Long id) throws SQLException {
        Session session = null;
        Books book = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            book = (Books) session.load(Books.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List getAll() throws SQLException {
        Session session = null;
        List<Books> book = new ArrayList<Books>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            book = session.createCriteria(Books.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List getByAuthorId(Object id) throws SQLException {
        Session session = null;
        List<Books> books = new ArrayList<Books>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            books = session.createCriteria(Books.class)
                    .createCriteria("author","author")
                        .add(Restrictions.eq("author.id", id))
                    .list();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return books;
    }

    @Override
    public void delete(Books book) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(book);
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
        Books book;
        try {
            book = getById(id);
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
