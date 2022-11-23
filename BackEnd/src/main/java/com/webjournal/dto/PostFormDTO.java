package com.webjournal.dto;
/*
  @author emilia
  @project WebJournal
  @class PostFormDTO
  @version 1.0.0
  @since 21.11.2022 - 18:39
*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostFormDTO {
    private String title;

    private String foreword;
    private String content;

    public PostFormDTO() {
    }

    public PostFormDTO(String title, String foreword, String content) {
        this.title = title;
        this.foreword = foreword;
        this.content = content;
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



    @Override
    public String toString() {
        return "PostFormDTO{" +
                ", title='" + title + '\'' +
                ", foreword='" + foreword + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
