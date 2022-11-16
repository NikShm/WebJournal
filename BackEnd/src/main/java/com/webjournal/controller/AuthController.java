package com.webjournal.controller;

import com.webjournal.entity.RefreshToken;
import com.webjournal.security.payload.request.LoginRequest;
import com.webjournal.entity.User;
import com.webjournal.security.payload.request.RegistrationRequest;
import com.webjournal.security.payload.response.MessageResponse;
import com.webjournal.security.payload.response.UserInfoResponse;
import com.webjournal.security.jwt.JwtUtils;
import com.webjournal.service.refreshtoken.RefreshTokenServiceImpl;
import com.webjournal.service.user.UserServiceImpl;
import freemarker.template.TemplateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
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
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;
    private final RefreshTokenServiceImpl refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserServiceImpl userService, RefreshTokenServiceImpl refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
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
        userService.create(registrationRequest);
        return ResponseEntity.ok(new MessageResponse("Successfully registered user"));
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyUser(@RequestParam(required = false) String token) {
        userService.verifyUser(token);
        return ResponseEntity.ok(new MessageResponse("Successfully verified account"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.toString().equals("anonymousUser")) {
            refreshTokenService.deleteByUser(principal);
        }

        ResponseCookie jwtAccessCookie = jwtUtils.getCleanJwtAccessCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("Successfully logged out"));
    }
}
