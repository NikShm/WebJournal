package com.webjournal.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UserUpdateRequest
 * @since 12/7/2022 - 02.43
 **/
public class UserUpdateRequest {
    @NotNull(message = "id is required")
    private Integer id;

    @NotBlank(message = "username is required")
    @Size(min = 3, max = 64, message = "username should be between 3 and 64 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "username can contain only Latin letters, numbers, underscores and hyphens")
    private String username;

    @Size(max = 150, message = "bio should contain 150 characters at most")
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
