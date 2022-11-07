package com.webjournal.controller;

import com.webjournal.security.payload.request.LoginRequest;
import com.webjournal.entity.User;
import com.webjournal.security.payload.request.RegistrationRequest;
import com.webjournal.security.payload.response.JwtResponse;
import com.webjournal.security.jwt.JwtUtils;
import com.webjournal.service.user.UserServiceImpl;
import freemarker.template.TemplateException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthController
 * @since 10/18/2022 - 20.28
 **/
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getRole().getRole());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) throws TemplateException, MessagingException, IOException {
        userService.create(registrationRequest);
        return ResponseEntity.ok("Successfully registered user");
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyUser(@RequestParam(required = false) String token) {
        userService.verifyUser(token);
        return ResponseEntity.ok("Successfully verified account");
    }
}
