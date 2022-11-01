package com.webjournal.dto.user;

import com.webjournal.dto.PostDTO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private String bio;
    private String profile_picture_path;
    private Set<AuthorDTO> followers;
    private Set<AuthorDTO> following;
    private List<PostDTO> posts;
    private Integer role;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    public UserDTO() {
    }

    public UserDTO(Integer id, String username, String password, String email, LocalDate birthDate, String bio, String profile_picture_path, Set<AuthorDTO> followers, Set<AuthorDTO> following, List<PostDTO> posts, Integer role, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.bio = bio;
        this.profile_picture_path = profile_picture_path;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
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

    public String getProfile_picture_path() {
        return profile_picture_path;
    }

    public void setProfile_picture_path(String profile_picture_path) {
        this.profile_picture_path = profile_picture_path;
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

    public Set<AuthorDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<AuthorDTO> followers) {
        this.followers = followers;
    }

    public Set<AuthorDTO> getFollowing() {
        return following;
    }

    public void setFollowing(Set<AuthorDTO> following) {
        this.following = following;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
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
                ", profile_picture_path='" + profile_picture_path + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", posts=" + posts +
                ", role=" + role +
                ", registeredAt=" + registeredAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
