package com.webjournal.dto;
/*
  @author emilia
  @project WebJournal
  @class PostFormDTO
  @version 1.0.0
  @since 21.11.2022 - 18:39
*/

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PostFormDTO {
    private String title;
    private String foreword;
    private String content;
    private List<TagDTO> tags;

    public PostFormDTO() {
    }

    public PostFormDTO(String title, String foreword, String content, List<TagDTO> tags) {
        this.title = title;
        this.foreword = foreword;
        this.content = content;
        this.tags = tags;
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

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "PostFormDTO{" +
                "title='" + title + '\'' +
                ", foreword='" + foreword + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                '}';
    }
}
