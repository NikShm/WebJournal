package com.webjournal.mail.context;

import com.webjournal.entity.User;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AccountVerificationMailContext
 * @since 11/1/2022 - 21.53
 **/
public class AccountVerificationMailContext extends AbstractMailContext {
    @Override
    public <T> void init(T context) {
        User user = (User) context;
        put("username", user.getUsername());
        setTemplateLocation("mail/email-verification.ftlh");
        setSubject("Complete your registration");
        setFrom("no-reply@webjournal.com");
        setTo(user.getEmail());
    }

    public void buildVerificationUrl(String baseURL, String token) {
        String url = UriComponentsBuilder.fromHttpUrl(baseURL).path("/auth/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
