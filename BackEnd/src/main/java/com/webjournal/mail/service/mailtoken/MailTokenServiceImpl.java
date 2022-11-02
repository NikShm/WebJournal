package com.webjournal.mail.service.mailtoken;

import com.webjournal.entity.MailToken;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.repository.MailTokenRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class MailTokenServiceImpl
 * @since 11/1/2022 - 20.13
 **/
@Service
public class MailTokenServiceImpl implements MailTokenService {
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(10);
    private static final Charset US_ASCII = StandardCharsets.US_ASCII;

    @Value("${application.mail.token.validity.in.hours}")
    private Integer tokenValidityInHours;

    private final MailTokenRepository repository;

    public MailTokenServiceImpl(MailTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public MailToken createMailToken() {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        MailToken mailToken = new MailToken();
        mailToken.setToken(tokenValue);
        mailToken.setExpiresAt(LocalDateTime.now().plusHours(tokenValidityInHours));
        return mailToken;
    }

    @Override
    public void saveMailToken(MailToken token) {
        repository.save(token);
    }

    @Override
    public MailToken getByToken(String token) {
        return repository.findByToken(token).orElseThrow(() -> new DatabaseFetchException("Could not find entity MailToken with token " + token));
    }

    @Override
    public void deleteToken(MailToken token) {
        repository.delete(token);
    }
}
