package com.webjournal.dto.user;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UserDTO
 * @since 9/17/2022 - 20.26
 **/
public class UserDTO {
    private Integer id;
    private String username;
    private String bio;
    private int followers;
    private int following;
    private boolean isFollowing;

    public UserDTO() {
    }

    public UserDTO(Integer id, String username, String bio, int followers, int following, boolean isFollowing) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.followers = followers;
        this.following = following;
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

    public boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean following) {
        isFollowing = following;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", isFollowing=" + isFollowing +
                '}';
    }
}
