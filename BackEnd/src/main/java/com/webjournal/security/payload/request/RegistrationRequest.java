package com.webjournal.security.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webjournal.validation.birthdate.BirthDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class RegistrationRequest
 * @since 11/1/2022 - 18.11
 **/
public class RegistrationRequest {
    @NotBlank
    @Size(min = 3, max = 64)
    private String username;

    @NotBlank
    @Size(min = 6, max = 32)
    private String password;

    //@NotBlank
    @Email
    @Size(max = 256)
    private String email;

    @NotNull
    @BirthDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String bio;
    private String role;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String username, String password, String email, LocalDate birthDate, String bio, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.bio = bio;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", bio='" + bio + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
