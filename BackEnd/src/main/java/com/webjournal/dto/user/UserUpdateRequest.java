package com.webjournal.dto.user;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UserUpdateRequest
 * @since 12/7/2022 - 02.43
 **/
public class UserUpdateRequest {
    private Integer id;
    private String username;
    private String bio;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(Integer id, String username, String bio) {
        this.id = id;
        this.username = username;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
