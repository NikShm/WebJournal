package com.webjournal.mail.service.mailtoken;

import com.webjournal.entity.MailToken;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class MailTokenService
 * @since 11/1/2022 - 20.15
 **/
public interface MailTokenService {

    MailToken createMailToken();

    void saveMailToken(MailToken token);

    MailToken getByToken(String token);

    void deleteToken(MailToken token);
}
