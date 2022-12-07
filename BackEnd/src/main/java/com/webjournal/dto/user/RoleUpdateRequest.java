package com.webjournal.dto.user;

import com.webjournal.enums.RoleType;

import javax.validation.constraints.NotNull;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class RoleUpdateRequest
 * @since 12/7/2022 - 12.59
 **/
public class RoleUpdateRequest {
    @NotNull(message = "id is required")
    private Integer id;

    @NotNull(message = "role is required")
    private RoleType role;

    public RoleUpdateRequest() {
    }

    public RoleUpdateRequest(Integer id, RoleType role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleUpdateRequest{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
