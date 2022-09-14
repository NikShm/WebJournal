package com.webjournal.dto;


import com.webjournal.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

/*
@author Микола
@project High_school_library
@class PostDTO
@version 1.0.0
@since 05.09.2022 - 19.46
*/
public class PostDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String category;
    private Integer count;
    private LocalDateTime createdAt;
    private List<AuthorDTO> authorList;

    public PostDTO() {
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.name = post.getName();
        this.description = post.getDescription();
        this.price = post.getPrice();
        this.category = post.getCategory();
        this.count = post.getCount();
        this.createdAt = post.getCreatedAt();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<AuthorDTO> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<AuthorDTO> authorList) {
        this.authorList = authorList;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", count=" + count +
                ", createdAt='" + createdAt + '\'' +
                ", authorList=" + authorList +
                '}';
    }
}
