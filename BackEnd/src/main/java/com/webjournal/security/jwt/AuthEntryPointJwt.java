package com.webjournal.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webjournal.exception.ApiErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthEntryPointJwt
 * @since 10/18/2022 - 19.28
 **/
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        HttpStatus status = authException instanceof BadCredentialsException || authException instanceof DisabledException ? BAD_REQUEST : UNAUTHORIZED;
        LOGGER.error("Unauthorized error: {}", authException.getMessage());

        String path = request.getServletPath();
        ApiErrorMessage errorMessage = new ApiErrorMessage(status.value(), status.getReasonPhrase(), LocalDateTime.now(), path, authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        mapper.writeValue(response.getOutputStream(), errorMessage);
    }
}
