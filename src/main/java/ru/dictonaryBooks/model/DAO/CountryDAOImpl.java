package ru.dictonaryBooks.model.DAO;

import org.hibernate.Session;
import ru.dictonaryBooks.CountryUtil;
import ru.dictonaryBooks.model.domain.Country;
import ru.dictonaryBooks.model.util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nasak on 23.02.14.
 */
public class CountryDAOImpl implements CountryDAO {
    @Override
    public void create(Country country) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(country);
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
    public void update(Country country) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(country);
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
    public Country getById(Long id) throws SQLException {
        //createList();
        Session session = null;
        Country country = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            country = (Country) session.load(Country.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return country;
    }

    @Override
    public List getAll() throws SQLException {
        //createList();
        Session session = null;
        List<Country> country = new ArrayList<Country>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            country = session.createCriteria(Country.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return country;
    }

    @Override
    public void delete(Country country) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(country);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    static {
        String[] countryList = CountryUtil.getCountry();
        //System.out.println("Is Fill in the table country");
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            long size = (Long) session.createQuery("SELECT COUNT(c) FROM Country c").uniqueResult();
            if (size == 0) {
                session.beginTransaction();
                for (String p : countryList) {
                    Country coun = new Country();
                    coun.setName(p);
                    session.save(coun);
                }
                session.getTransaction().commit();
                System.out.println("Fill in the table country");
            }
        } catch (Exception e) {
            System.err.println("Ошибка I/O");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
