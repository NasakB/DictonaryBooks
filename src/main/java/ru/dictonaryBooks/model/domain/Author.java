package ru.dictonaryBooks.model.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by nasak on 23.02.14.
 */
@Entity
@Table(name = "AUTHOR")
public class Author {
    private Long id;
    private String name;
    private Country country;

    public Author() {
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

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name ="ID_COUNTRY")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
