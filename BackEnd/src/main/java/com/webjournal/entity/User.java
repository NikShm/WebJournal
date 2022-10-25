package com.webjournal.entity;

import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="login")
    @Size(max = 60, message = "USERNAME must be between 1 and 60 characters long")
    @NotBlank(message = "USERNAME may not be null")
    private String username;
    @NotBlank(message = "PASSWORD may not be null")
    @Size(max = 32, message = "PASSWORD must be between 1 and 32 characters long")
    private String password;
    @Size(max = 256, message = "USERNAME must be between 1 and 256 characters long")
    private String email;

    @Column(name="birth_date")
    @NotNull(message = "BIRTH DATE may not be null")
    private LocalDate birthDate;
    @Size(max = 150, message = "BIO must be between 1 and 150 characters long")
    private String bio;

    @ManyToMany
    @JoinTable(name="follow",
            joinColumns= @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="following_user_id"))
    private Set<User> followers;

    @NotBlank(message = "PROFILE PICTURE PATH may not be blank")
    @Size(max = 512, message = "PROFILE PICTURE PATH must be between 1 and 512 characters long")
    private String profile_picture_path;
    @Formula("(select count(*) from follow f where f.user_id = {alias}.id)")
    private int countFollowers;

    @ManyToMany(mappedBy = "followers")
    private Set<User> following;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    @NotNull(message = "ROLE may not be null")
    private Role role;

    @Column(name="created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(Integer id, String username, String password, String email, LocalDate birthDate, String bio, Set<User> followers, String profile_picture_path, int countFollowers, Set<User> following, List<Post> posts, Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.bio = bio;
        this.followers = followers;
        this.profile_picture_path = profile_picture_path;
        this.countFollowers = countFollowers;
        this.following = following;
        this.posts = posts;
        this.role = role;
        this.createdAt = createdAt;
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

    public String getProfile_picture_path() {
        return profile_picture_path;
    }

    public void setProfile_picture_path(String profile_picture_path) {
        this.profile_picture_path = profile_picture_path;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getCountFollowers() {
        return countFollowers;
    }

    public void setCountFollowers(int countFollowers) {
        this.countFollowers = countFollowers;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", bio='" + bio + '\'' +
                ", posts=" + posts +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
