package com.webjournal.dto.post;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class PostPreviewDTO
 * @since 12/4/2022 - 02.22
 **/
public class PostPreviewDTO {
    private Integer id;
    private String title;
    private String foreword;
    private String tag;

    public PostPreviewDTO() {
    }

    public PostPreviewDTO(Integer id, String title, String foreword, String tag) {
        this.id = id;
        this.title = title;
        this.foreword = foreword;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "PostPreviewDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", foreword='" + foreword + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
