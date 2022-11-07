package com.webjournal.mail.service.mail;

import com.webjournal.mail.context.AbstractMailContext;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class MailServiceImpl
 * @since 11/1/2022 - 21.39
 **/
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final Configuration configuration;

    public MailServiceImpl(JavaMailSender mailSender, Configuration configuration) {
        this.mailSender = mailSender;
        this.configuration = configuration;
    }

    @Override
    public void sendMail(AbstractMailContext mail) throws IOException, TemplateException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(mail.getTemplateLocation()).process(mail.getContext(), stringWriter);
        String mailContent = stringWriter.getBuffer().toString();

        mimeMessageHelper.setTo(mail.getTo());
        mimeMessageHelper.setSubject(mail.getSubject());
        mimeMessageHelper.setFrom(mail.getFrom());
        mimeMessageHelper.setText(mailContent, true);

        mailSender.send(message);
    }
}
