package com.webjournal.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/*
@author Микола
@project WebJournal
@class PostListDTO
@version 1.0.0
@since 11.11.2022 - 19.28
*/
public class PostListDTO {
    private Integer id;
    private String title;
    private String foreword;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedAt;

    public PostListDTO() {
    }

    public PostListDTO(Integer id, String title, String foreword, LocalDateTime publishedAt) {
        this.id = id;
        this.title = title;
        this.foreword = foreword;
        this.publishedAt = publishedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "PostListDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", foreword='" + foreword + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
