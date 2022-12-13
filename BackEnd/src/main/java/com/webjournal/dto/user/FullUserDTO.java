package com.webjournal.dto.user;

import com.webjournal.enums.RoleType;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class FullUserDTO
 * @since 12/7/2022 - 11.37
 **/
public class FullUserDTO {
    private Integer id;
    private String username;
    private String email;
    private String bio;
    private int followers;
    private int following;
    private RoleType role;
    private boolean isFollowing;

    public FullUserDTO() {
    }

    public FullUserDTO(Integer id, String username, String email, String bio, int followers, int following, RoleType role, boolean isFollowing) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.followers = followers;
        this.following = following;
        this.role = role;
        this.isFollowing = isFollowing;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean following) {
        isFollowing = following;
    }

    @Override
    public String toString() {
        return "FullUserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", role=" + role +
                ", isFollowing=" + isFollowing +
                '}';
    }
}
