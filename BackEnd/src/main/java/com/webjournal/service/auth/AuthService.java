package com.webjournal.service.auth;

import com.webjournal.dto.auth.RegistrationRequest;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthService
 * @since 11/16/2022 - 13.02
 **/
public interface AuthService {
    Boolean checkIfUserExistsByUsername(String username);
    Boolean checkIfUserExistsByEmail(String email);
    void register(RegistrationRequest registrationRequest) throws TemplateException, MessagingException, IOException;
    void verifyUser(String token);
}
