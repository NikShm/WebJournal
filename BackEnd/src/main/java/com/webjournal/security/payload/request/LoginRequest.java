package com.webjournal.security.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class LoginRequest
 * @since 10/18/2022 - 20.37
 **/
public class LoginRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
