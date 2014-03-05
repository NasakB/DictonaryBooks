package ru.dictonaryBooks.model.DAO;

import ru.dictonaryBooks.model.domain.Country;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by nasak on 23.02.14.
 */
public interface CountryDAO {
    public void create(Country country) throws SQLException;
    public void update(Country country) throws SQLException;
    public Country getById(Long id) throws SQLException;
    public List getAll() throws SQLException;
    public void delete(Country country) throws SQLException;
    //public void createList() throws SQLException;
}
