package com.webjournal.dto.auth;

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
    @NotBlank(message = "username is required")
    @Size(min = 3, max = 64, message = "username should be between 3 and 64 characters long")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 32, message = "password should be between 6 and 32 characters long")
    private String password;

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    @Size(max = 256, message = "email should contain at most 256 characters")
    private String email;

    @NotNull(message = "birth date is required")
    @BirthDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String username, String password, String email, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
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

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
