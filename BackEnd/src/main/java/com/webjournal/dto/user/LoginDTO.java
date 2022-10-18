package com.webjournal.dto.user;

import javax.validation.constraints.NotBlank;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class LoginDTO
 * @since 10/18/2022 - 20.37
 **/
public class LoginDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
