package ru.dictonaryBooks.model.DAO;

import ru.dictonaryBooks.model.domain.Books;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by nasak on 23.02.14.
 */
public interface BooksDAO {
    public void create(Books books) throws SQLException;
    public void update(Books books) throws SQLException;
    public Books getById(Long id) throws SQLException;
    public List getAll() throws SQLException;
    public List getByAuthorId(Object id) throws SQLException;
    public void delete(Books books) throws SQLException;
    public void delete(Long id) throws SQLException;
}
