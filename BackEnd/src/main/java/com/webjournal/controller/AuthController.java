package com.webjournal.controller;

import com.webjournal.entity.RefreshToken;
import com.webjournal.dto.auth.LoginRequest;
import com.webjournal.entity.User;
import com.webjournal.dto.auth.RegistrationRequest;
import com.webjournal.dto.MessageResponse;
import com.webjournal.dto.auth.UserInfoResponse;
import com.webjournal.security.jwt.JwtUtils;
import com.webjournal.service.auth.AuthServiceImpl;
import com.webjournal.service.refreshtoken.RefreshTokenServiceImpl;
import freemarker.template.TemplateException;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthController
 * @since 10/18/2022 - 20.28
 **/
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthServiceImpl authService;
    private final RefreshTokenServiceImpl refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, AuthServiceImpl authService, RefreshTokenServiceImpl refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();

        ResponseCookie jwtAccessCookie = jwtUtils.generateJwtAccessCookie(userDetails);

        RefreshToken refreshToken = refreshTokenService.create(userDetails.getId());
        ResponseCookie jwtRefreshCookie = jwtUtils.generateJwtRefreshCookie(refreshToken.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getRole().getRole()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyExpiration(jwtUtils.getRefreshJwtFromCookies(request));
        ResponseCookie jwtAccessCookie = jwtUtils.generateJwtAccessCookie(refreshToken.getUser());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshToken.getToken())
                .body(new MessageResponse("Access token is refreshed successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) throws TemplateException, MessagingException, IOException {
        authService.register(registrationRequest);
        return ResponseEntity.ok(new MessageResponse("Successfully registered user"));
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyUser(@RequestParam(required = false) String token) {
        authService.verifyUser(token);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:4200/login"))
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.toString().equals("anonymousUser")) {
            refreshTokenService.deleteByUser((User) principal);
        }

        ResponseCookie jwtAccessCookie = jwtUtils.getCleanJwtAccessCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("Successfully logged out"));
    }
}
