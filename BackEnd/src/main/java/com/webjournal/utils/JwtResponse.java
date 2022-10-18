package com.webjournal.utils;

import com.webjournal.enums.RoleType;

import java.util.List;
import java.util.Set;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class JwtResponse
 * @since 10/18/2022 - 21.27
 **/
public class JwtResponse {
    private String token; //access
    private Integer id;
    private String username;
    private String email;
    private RoleType role;

    public JwtResponse(String token, Integer id, String username, String email, RoleType role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
