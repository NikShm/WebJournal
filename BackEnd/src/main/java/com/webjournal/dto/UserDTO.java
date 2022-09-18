package com.webjournal.dto;

import com.webjournal.enums.RoleType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private String password;
    private String email;
    private LocalDate birthDate;
    private String bio;
    private String profilePicturePath;
    private List<AuthorDTO> followers;
    private List<AuthorDTO> following;
    private RoleType role;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    public UserDTO() {
    }

    public UserDTO(Integer id, String username, String password, String email, LocalDate birthDate, String bio, String profilePicturePath, List<AuthorDTO> followers, List<AuthorDTO> following, RoleType role, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.bio = bio;
        this.profilePicturePath = profilePicturePath;
        this.followers = followers;
        this.following = following;
        this.role = role;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public List<AuthorDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<AuthorDTO> followers) {
        this.followers = followers;
    }

    public List<AuthorDTO> getFollowing() {
        return following;
    }

    public void setFollowing(List<AuthorDTO> following) {
        this.following = following;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", bio='" + bio + '\'' +
                ", profilePicturePath='" + profilePicturePath + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", role=" + role +
                ", registeredAt=" + registeredAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
