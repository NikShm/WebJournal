package com.webjournal.dto.user;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthorDTO
 * @since 9/18/2022 - 12.47
 **/
public class AuthorDTO {
    private Integer id;
    private String username;
    private Integer followers;

    public AuthorDTO() {
    }

    public AuthorDTO(Integer id, String username, Integer followers) {
        this.id = id;
        this.username = username;
        this.followers = followers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", followers=" + followers +
                '}';
    }
}
