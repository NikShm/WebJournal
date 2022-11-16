package com.webjournal.dto.user;

import com.webjournal.enums.RoleType;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UserInfoResponse
 * @since 10/18/2022 - 21.27
 **/
public class UserInfoResponse {
    private Integer id;
    private String username;
    private RoleType role;

    public UserInfoResponse() {
    }

    public UserInfoResponse(Integer id, String username, RoleType role) {
        this.id = id;
        this.username = username;
        this.role = role;
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
