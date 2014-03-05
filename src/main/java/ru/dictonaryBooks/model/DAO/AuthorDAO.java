package ru.dictonaryBooks.model.DAO;


import ru.dictonaryBooks.model.domain.Author;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by nasak on 21.02.14.
 */
public interface AuthorDAO {
    public void create(Author author) throws SQLException;
    public void update(Author author) throws SQLException;
    public Author getById(Long id) throws SQLException;
    public List getAll() throws SQLException;
    public void delete(Author author) throws SQLException;
    public void delete(Long id) throws SQLException;
}
