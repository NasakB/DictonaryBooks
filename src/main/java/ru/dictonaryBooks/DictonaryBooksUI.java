package ru.dictonaryBooks;

import com.vaadin.annotations.*;
import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.*;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.*;
import ru.dictonaryBooks.model.DAO.Factory;
import ru.dictonaryBooks.model.domain.*;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by nasak on 06.03.14.
 */
@Title("Addressbook")
@Theme("mytheme")
@SuppressWarnings("serial")
public class DictonaryBooksUI extends UI {
    private VerticalLayout rightLayout = new VerticalLayout();

    private Table authorTable = new Table();
    private Table bookTable = new Table();

    private Button addAuthorButton = new Button("Добавить");
    private Button removeAuthorButton = new Button("Удалить");
    private Button addBookButton = new Button("Добавить");
    private Button removeBookButton = new Button("Удалить");

    private AddWindow addAuthorWindow = new AddWindow("Добавить автора");
    private AddWindow addBookWindow = new AddWindow("Добавить книгу");

    private FieldGroup authorFieldGroup = new FieldGroup();
    private FieldGroup bookFieldGroup = new FieldGroup();

    private static final String AUTHOR = "Автор";
    private static final String COUNTRY = "Страна";

    private static final String TITLE = "Название";
    private static final String GENRE = "Жанр";

    private static final String PROPERTY_NAME_COUNTRY = "name";
    private static final String PROPERTY_ID_COUNTRY = "id";

    private static final String[] authorsColumn = new String[]{AUTHOR, COUNTRY};
    private static final String[] booksColumn = new String[]{TITLE, GENRE};

    IndexedContainer authorsContainer = createAuthorDummyDatasource();
    IndexedContainer booksContainer = new IndexedContainer();
    PropertysetItem authorItem;
    PropertysetItem bookItem;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DictonaryBooksUI.class, widgetset = "ru.dictonaryBooks.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        initLayout();
        initAuthorList();
        //initBookList();
        initAddAuthorWindow();
        initAddBookWindow();
        initAddRemoveButtons();
    }

    private void initLayout() {
        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        setContent(splitPanel);

        VerticalLayout leftLayout = new VerticalLayout();
        splitPanel.addComponent(leftLayout);
        splitPanel.addComponent(rightLayout);

        leftLayout.addComponent(authorTable);
        rightLayout.addComponent(bookTable);

        HorizontalLayout leftButtonLayout = new HorizontalLayout();
        HorizontalLayout rightButtonLayout = new HorizontalLayout();

        leftLayout.addComponent(leftButtonLayout);
        rightLayout.addComponent(rightButtonLayout);

        leftButtonLayout.setSpacing(true);
        leftButtonLayout.setSizeUndefined();
        leftButtonLayout.addComponent(addAuthorButton);
        leftButtonLayout.setComponentAlignment(addAuthorButton, Alignment.MIDDLE_RIGHT);
        leftButtonLayout.setExpandRatio(addAuthorButton, 1);
        leftButtonLayout.addComponent(removeAuthorButton);
        leftButtonLayout.setComponentAlignment(removeAuthorButton, Alignment.MIDDLE_RIGHT);
        removeAuthorButton.setEnabled(false);

        rightButtonLayout.setSpacing(true);
        rightButtonLayout.addComponent(addBookButton);
        rightButtonLayout.setComponentAlignment(addBookButton, Alignment.MIDDLE_RIGHT);
        rightButtonLayout.setExpandRatio(addBookButton, 1);
        rightButtonLayout.addComponent(removeBookButton);
        rightButtonLayout.setComponentAlignment(removeBookButton, Alignment.MIDDLE_RIGHT);


        leftLayout.setSizeFull();
        rightLayout.setSizeFull();

        leftLayout.setExpandRatio(authorTable, 1);
        rightLayout.setExpandRatio(bookTable, 1);
        authorTable.setSizeFull();
        bookTable.setSizeFull();

        leftButtonLayout.setWidth("100%");
        rightButtonLayout.setWidth("100%");

        rightLayout.setVisible(false);
    }

    private void initAuthorList() {
        authorTable.setContainerDataSource(authorsContainer);
        authorTable.setVisibleColumns(new String[]{AUTHOR, COUNTRY});
        authorTable.setSelectable(true);
        authorTable.setImmediate(true);
    }

    private void initBookList() {
        bookTable.setContainerDataSource(booksContainer);
        bookTable.setVisibleColumns(new String[]{TITLE, GENRE});
        bookTable.setSelectable(true);
        bookTable.setImmediate(true);
        removeBookButton.setEnabled(false);
    }

    private void initAddAuthorWindow() {

        TextField nameField = new TextField("Имя");
        addAuthorWindow.addComponent(nameField);
        nameField.setWidth("100%");
        authorFieldGroup.bind(nameField, "name");
        ComboBox countryBox = new ComboBox("Страна", getCountryContainer());
        countryBox.setInputPrompt("Страна не выбрана");
        countryBox.setItemCaptionPropertyId(PROPERTY_NAME_COUNTRY);
        countryBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        countryBox.setWidth("100%");

        addAuthorWindow.addComponent(countryBox);
        authorFieldGroup.bind(countryBox, "country");

        authorFieldGroup.setBuffered(false);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setSizeUndefined();
        addAuthorWindow.addComponent(buttonLayout);


        Button add = new Button("Добавить", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

                Author author = new Author();
                author.setName(authorItem.getItemProperty("name").getValue().toString());
                author.setCountry(getCountryById(Long.valueOf(authorItem.getItemProperty("country").getValue().toString())));
                try {
                    Factory.getInstance().getAuthorDAO().create(author);
                } catch (SQLException e) { e.printStackTrace(); }
                authorsContainer = createAuthorDummyDatasource();
                initAuthorList();
                addAuthorWindow.close();
            }
        });
        buttonLayout.addComponent(add);

        Button cancel = new Button("Отмена", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                addAuthorWindow.close();
            }
        });
        addAuthorWindow.addComponent(cancel);
        buttonLayout.addComponent(cancel);
    }

    private void initAddBookWindow() {

        TextField titleField = new TextField("Название");
        addBookWindow.addComponent(titleField);
        titleField.setWidth("100%");
        bookFieldGroup.bind(titleField, "title");

        TextField genreField = new TextField("Жанр");
        addBookWindow.addComponent(genreField);
        bookFieldGroup.bind(genreField, "genre");

        bookFieldGroup.setBuffered(false);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setSizeUndefined();
        addBookWindow.addComponent(buttonLayout);


        Button add = new Button("Добавить", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Books book = new Books();
                book.setTitle(bookItem.getItemProperty("title").getValue().toString());
                book.setGenre(bookItem.getItemProperty("genre").getValue().toString());
                book.setAuthor(getAuthorById(Long.valueOf(authorTable.getValue().toString())));
                try {
                    Factory.getInstance().getBooksDAO().create(book);
                } catch (SQLException e) { e.printStackTrace(); }
                booksContainer = createBookDummyDatasource(authorTable.getValue());
                initBookList();
                addBookWindow.close();
            }
        });
        buttonLayout.addComponent(add);

        Button cancel = new Button("Отмена", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                addBookWindow.close();
            }
        });
        addBookWindow.addComponent(cancel);
        buttonLayout.addComponent(cancel);
    }

    private void initAddRemoveButtons() {
        authorTable.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Object contactId = authorTable.getValue();
                if (contactId != null) {
                    booksContainer = createBookDummyDatasource(contactId);
                    initBookList();
                    rightLayout.setVisible(contactId != null);
                    removeAuthorButton.setEnabled(contactId != null);
                }
            }
        });
        bookTable.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Object contactId = bookTable.getValue();
                if (contactId != null) {
                    System.out.println(contactId);
                    //editorLayout.setVisible(contactId != null);
                    removeBookButton.setEnabled(contactId != null);
                }
            }
        });

        addAuthorButton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                getCurrent().addWindow(addAuthorWindow);
                authorItem = new PropertysetItem();
                authorItem.addItemProperty("name", new ObjectProperty("", String.class));
                authorItem.addItemProperty("country", new ObjectProperty(null, String.class));

                authorFieldGroup.setItemDataSource(authorItem);
            }
        });

        addBookButton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                System.out.println("clic add book");
                getCurrent().addWindow(addBookWindow);
                bookItem = new PropertysetItem();
                bookItem.addItemProperty("title", new ObjectProperty("", String.class));
                bookItem.addItemProperty("genre", new ObjectProperty("", String.class));

                bookFieldGroup.setItemDataSource(bookItem);
            }
        });

        removeAuthorButton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                Object contactId = authorTable.getValue();
                List<Books> books = null;
                try {
                    books = Factory.getInstance().getBooksDAO().getByAuthorId(contactId);

                    for (int i = 0; i < books.size(); i++) {
                        Factory.getInstance().getBooksDAO().delete(books.get(i).getId());
                    }
                    Factory.getInstance().getAuthorDAO().delete(Long.valueOf(contactId.toString()));
                } catch (SQLException e) {e.printStackTrace();}
                authorTable.removeItem(contactId);
                rightLayout.setVisible(false);
                removeAuthorButton.setEnabled(false);
            }
        });

        removeBookButton.addClickListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                Object contactId = bookTable.getValue();
                try {
                    Factory.getInstance().getBooksDAO().delete(Long.valueOf(contactId.toString()));
                } catch (SQLException e) {e.printStackTrace();}
                bookTable.removeItem(contactId);
                removeAuthorButton.setEnabled(false);
            }
        });
    }

    private IndexedContainer createAuthorDummyDatasource() {
        IndexedContainer ic = new IndexedContainer();

        for (String p : authorsColumn) {
            ic.addContainerProperty(p, String.class, "");
        }

        List<Author> author = null;
        try {
            author = Factory.getInstance().getAuthorDAO().getAll();
        } catch (SQLException e) { e.printStackTrace(); }

        for (int i = 0; i < author.size(); ++i) {
            Item item = ic.addItem(author.get(i).getId());
            item.getItemProperty(AUTHOR).setValue(author.get(i).getName());

            try {
                item.getItemProperty(COUNTRY).setValue(author.get(i).getCountry().getName());
            } catch (NullPointerException e) {
                item.getItemProperty(COUNTRY).setValue("");
            }
        }
        return ic;
    }

    private IndexedContainer createBookDummyDatasource(Object contactId) {
        IndexedContainer ic = new IndexedContainer();
        for (String p : booksColumn) {
            ic.addContainerProperty(p, String.class, "");
        }
        List<Books> books = null;
        try {
            books = Factory.getInstance().getBooksDAO().getByAuthorId(contactId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < books.size(); ++i) {
            Item item = ic.addItem(books.get(i).getId());
            item.getItemProperty(TITLE).setValue(books.get(i).getTitle());
            item.getItemProperty(GENRE).setValue(books.get(i).getGenre());
        }
        return ic;
    }

    public IndexedContainer getCountryContainer() {
        IndexedContainer c = new IndexedContainer();
        fillCountryContainer(c);
        return c;
    }

    private void fillCountryContainer(IndexedContainer container) {
        container.addContainerProperty(PROPERTY_NAME_COUNTRY, String.class, null);
        container.addContainerProperty(PROPERTY_ID_COUNTRY, String.class, null);

        List<Country> countries = null;
        try {
            countries = Factory.getInstance().getCountryDAO().getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < countries.size(); i++) {
            String name = countries.get(i).getName();
            String id = String.valueOf(countries.get(i).getId());
            Item item = container.addItem(id);
            item.getItemProperty(PROPERTY_NAME_COUNTRY).setValue(name);
            item.getItemProperty(PROPERTY_ID_COUNTRY).setValue(id);

        }
        container.sort(new Object[]{PROPERTY_NAME_COUNTRY},
                new boolean[]{true});
    }

    private Author getAuthorById(Long id) {
        Author author = null;
        try {
            author = Factory.getInstance().getAuthorDAO().getById(id);
        } catch (SQLException e) { e.printStackTrace(); }
        return author;
    }

    private Country getCountryById(Long id) {
        Country country = null;
        try {
            country = Factory.getInstance().getCountryDAO().getById(id);
        } catch (SQLException e) { e.printStackTrace(); }
        return country;
    }
}
