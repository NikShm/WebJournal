package com.webjournal.dto;


import com.webjournal.entities.Author;

import java.time.LocalDateTime;
import java.util.List;

/*
@author Микола
@project High_school_library
@class AuthorDTO
@version 1.0.0
@since 05.09.2022 - 21.53
*/
public class AuthorDTO {
    private Integer id;
    private String name;
    private String surname;
    private LocalDateTime createdAt;
    private List<PostDTO> post;

    public AuthorDTO() {
    }

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.surname = author.getSurname();
        this.createdAt = author.getCreatedAt();
    }

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

    public List<PostDTO> getBooks() {
        return post;
    }

    public void setBooks(List<PostDTO> post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", createdAt=" + createdAt +
                ", post=" + post +
                '}';
    }
}
