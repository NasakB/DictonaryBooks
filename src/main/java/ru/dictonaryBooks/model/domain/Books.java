package ru.dictonaryBooks.model.domain;

import javax.persistence.*;

/**
 * Created by nasak on 23.02.14.
 */
@Entity
@Table(name = "BOOKS")
public class Books {

    private Long id;
    private String title;
    private String genre;

    private Author author;

    public Books() {
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }
    @Column(name = "GENRE")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @ManyToOne
    @JoinColumn(name ="ID_AUTHOR")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }


}
