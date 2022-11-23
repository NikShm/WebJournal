package com.webjournal.security.jwt;

import com.webjournal.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class JwtUtils
 * @since 10/18/2022 - 18.48
 **/
@Component
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${application.jwt.secretKey}")
    private String secretKey;
    @Value("${application.jwt.accessToken.validity.in.minutes}")
    private Integer tokenValidityInMinutes;
    @Value("${application.jwt.accessCookieName}")
    private String accessCookie;
    @Value("${application.jwt.refreshCookieName}")
    private String refreshCookie;

    public String generateJwtToken(String email, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .setSubject(email)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(tokenValidityInMinutes)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is not supported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public ResponseCookie generateJwtAccessCookie(User userPrincipal) {
        String jwt = generateJwtToken(userPrincipal.getEmail(), userPrincipal.getAuthorities());
        return generateCookie(accessCookie, jwt, "/api");
    }

    public ResponseCookie generateJwtRefreshCookie(String refreshToken) {
        return generateCookie(refreshCookie, refreshToken, "/api/auth/refresh-token");
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie
                .from(name, value)
                .path(path)
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
    }

    public ResponseCookie getCleanJwtAccessCookie() {
        return ResponseCookie
                .from(accessCookie, "")
                .path("/api")
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return ResponseCookie
                .from(refreshCookie, "")
                .path("/api/auth/refresh-token")
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    public String getAccessJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, accessCookie);
    }

    public String getRefreshJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, refreshCookie);
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else return null;
    }
}
