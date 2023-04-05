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
    private String bio;

    public AuthorDTO() {
    }

    public AuthorDTO(Integer id, String username, Integer followers, String bio) {
        this.id = id;
        this.username = username;
        this.followers = followers;
        this.bio = bio;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", followers=" + followers +
                ", bio='" + bio + '\'' +
                '}';
    }
}
