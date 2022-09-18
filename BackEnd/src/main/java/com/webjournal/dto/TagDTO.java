package com.webjournal.dto;

import java.time.LocalDateTime;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class TagDTO
 * @since 9/18/2022 - 13.00
 **/
public class TagDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;

    public TagDTO() {
    }

    public TagDTO(Integer id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
