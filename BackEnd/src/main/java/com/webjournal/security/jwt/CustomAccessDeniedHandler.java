package com.webjournal.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webjournal.utils.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class CustomAccessDeniedHandler
 * @since 10/19/2022 - 00.33
 **/
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        LOGGER.error("Forbidden error: {}", accessDeniedException.getMessage());

        String path = request.getServletPath();
        ErrorMessage errorMessage = new ErrorMessage(FORBIDDEN.value(), FORBIDDEN.getReasonPhrase(), LocalDateTime.now(), path, accessDeniedException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        mapper.writeValue(response.getOutputStream(), errorMessage);
    }
}
