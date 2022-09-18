package com.webjournal.dto;

import java.time.LocalDateTime;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class CommentDTO
 * @since 9/18/2022 - 12.44
 **/
public class CommentDTO {
    private Integer id;
    private AuthorDTO author;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentDTO() {
    }

    public CommentDTO(Integer id, AuthorDTO author, String text, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
