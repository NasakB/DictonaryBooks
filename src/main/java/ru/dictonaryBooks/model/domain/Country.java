package ru.dictonaryBooks.model.domain;

import javax.persistence.*;

/**
 * Created by nasak on 23.02.14.
 */
@Entity
@Table(name = "Country")
public class Country {
    private Long id;
    private String name;

    public Country() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
