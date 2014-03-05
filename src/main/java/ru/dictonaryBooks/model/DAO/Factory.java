package ru.dictonaryBooks.model.DAO;

/**
 * Created by nasak on 21.02.14.
 */
public class Factory {
    private static AuthorDAO authorDAO = null;
    private static BooksDAO booksDAO = null;
    private static CountryDAO countryDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public AuthorDAO getAuthorDAO(){
        if (authorDAO == null){
            authorDAO = new AuthorDAOImpl();
        }
        return authorDAO;
    }

    public BooksDAO getBooksDAO(){
        if (booksDAO == null){
            booksDAO = new BooksDAOImpl();
        }
        return booksDAO;
    }

    public CountryDAO getCountryDAO(){
        if (countryDAO == null){
            countryDAO = new CountryDAOImpl();
        }
        return countryDAO;
    }
}
