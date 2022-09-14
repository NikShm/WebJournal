package com.webjournal.entities;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/*
@author Микола
@project High_school_library
@class Author
@version 1.0.0
@since 05.09.2022 - 18.32
*/
@Entity
@Table(name = "author")
public class Author {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 128, nullable = false)
    private String name;
    @Column(name = "surname", length = 128, nullable = false)
    private String surname;
    @Column(name="createdat", nullable = false)
    private LocalDateTime createdAt;
    @ManyToMany(mappedBy = "authorList",fetch = FetchType.LAZY)
    private List<Post> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Post> getBooks() {
        return books;
    }

    public void setBooks(List<Post> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
