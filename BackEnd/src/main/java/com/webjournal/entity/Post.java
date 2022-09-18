package com.webjournal.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne()
    @JoinColumn(name="author_id")
    private User authorId;

    @Column(name="title")
    private String title;

    @Column(name="foreword")
    private String foreword;

    @Column(name="content")
    private String content;

    @Column(name="photo_path")
    private String photoPath;

    @Column(name="likes")
    private int likes;

    @Column(name="is_approved")
    private boolean isApproved;

    @Column(name="published_at")
    private LocalDateTime publishedAt;

    @ManyToMany
    @JoinTable(name="post_tag",
            joinColumns= @JoinColumn(name="post_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id"))
    private Set<Tag> postTags;

    public Post() {
    }

    public Post(int id, LocalDateTime createdAt, LocalDateTime updatedAt, User authorId, String title, String foreword, String content, String photoPath, int likes, boolean isApproved, LocalDateTime publishedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorId = authorId;
        this.title = title;
        this.foreword = foreword;
        this.content = content;
        this.photoPath = photoPath;
        this.likes = likes;
        this.isApproved = isApproved;
        this.publishedAt = publishedAt;
    }

    public Post(LocalDateTime createdAt, LocalDateTime updatedAt, User authorId, String title, String foreword, String content, String photoPath, int likes, boolean isApproved, LocalDateTime publishedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorId = authorId;
        this.title = title;
        this.foreword = foreword;
        this.content = content;
        this.photoPath = photoPath;
        this.likes = likes;
        this.isApproved = isApproved;
        this.publishedAt = publishedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForeword() {
        return foreword;
    }

    public void setForeword(String foreword) {
        this.foreword = foreword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", foreword='" + foreword + '\'' +
                ", content='" + content + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", likes=" + likes +
                ", isApproved=" + isApproved +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
