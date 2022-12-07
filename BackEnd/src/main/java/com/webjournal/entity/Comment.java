package com.webjournal.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="comment", schema="public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User author;

    @NotBlank(message = "COMMENT TEXT may not be blank")
    @Size(max = 512, message = "COMMENT TEXT must be between 1 and 500 characters long")
    private String text;

    @Column(name="post_id", updatable = false)
    private Integer postId;

    @Column(name="created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Comment() {
    }

    public Comment(Integer id, User author, String text, Integer postId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.postId = postId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", postId=" + postId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
