package com.webjournal.mail.service.mail;

import com.webjournal.mail.context.AbstractMailContext;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class MailService
 * @since 11/1/2022 - 21.39
 **/
public interface MailService {
    void sendMail(AbstractMailContext mail) throws IOException, TemplateException, MessagingException;
}
