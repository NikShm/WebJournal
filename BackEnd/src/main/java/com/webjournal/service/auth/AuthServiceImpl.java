package com.webjournal.service.auth;

import com.webjournal.dto.auth.RegistrationRequest;
import com.webjournal.entity.MailToken;
import com.webjournal.entity.User;
import com.webjournal.enums.RoleType;
import com.webjournal.exception.RegistrationException;
import com.webjournal.mail.context.AccountVerificationMailContext;
import com.webjournal.mail.service.mail.MailServiceImpl;
import com.webjournal.mail.service.mailtoken.MailTokenServiceImpl;
import com.webjournal.repository.UserRepository;
import com.webjournal.service.role.RoleServiceImpl;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthServiceImpl
 * @since 11/16/2022 - 13.02
 **/
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository repository;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    private final MailTokenServiceImpl mailTokenService;
    private final MailServiceImpl mailService;
    @Value("${site.base.url.http}")
    private String baseURL;

    public AuthServiceImpl(UserRepository repository, RoleServiceImpl roleService, PasswordEncoder passwordEncoder, MailTokenServiceImpl mailTokenService, MailServiceImpl mailService) {
        this.repository = repository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mailTokenService = mailTokenService;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " was not found."));
    }

    @Override
    public Boolean checkIfUserExistsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Boolean checkIfUserExistsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void register(RegistrationRequest registrationRequest) throws TemplateException, MessagingException, IOException {
        if (checkIfUserExistsByUsername(registrationRequest.getUsername())) {
            throw new RegistrationException("this username is already taken");
        }
        if (checkIfUserExistsByEmail(registrationRequest.getEmail())) {
            throw new RegistrationException("email is already in use");
        }
        User createdUser = new User();
        createdUser.setUsername(registrationRequest.getUsername());
        createdUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        createdUser.setEmail(registrationRequest.getEmail());
        createdUser.setAccountVerified(false);
        createdUser.setRole(roleService.getRoleByRoleType(RoleType.AUTHOR));

        createdUser = repository.save(createdUser);
        sendRegistrationConfirmationEmail(createdUser);
    }

    private void sendRegistrationConfirmationEmail(User user) throws TemplateException, MessagingException, IOException {
        MailToken mailToken = mailTokenService.createMailToken();
        mailToken.setUser(user);
        mailTokenService.saveMailToken(mailToken);
        AccountVerificationMailContext mailContext = new AccountVerificationMailContext();
        mailContext.init(user);
        mailContext.buildVerificationUrl(baseURL, mailToken.getToken());

        mailService.sendMail(mailContext);
    }

    @Override
    public void verifyUser(String token) {
        if (!StringUtils.hasText(token)) {
            throw new RegistrationException("token is empty");
        }
        MailToken mailToken = mailTokenService.getByToken(token);
        if (mailToken == null || !token.equals(mailToken.getToken()) || mailToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            mailTokenService.deleteToken(mailToken);
            throw new RegistrationException("mail token is not valid");
        }
        User user = repository.findById(mailToken.getUser().getId()).orElse(null);
        if (user == null) {
            throw new RegistrationException("this user doesn't exist");
        }
        user.setAccountVerified(true);
        repository.save(user);

        mailTokenService.deleteToken(mailToken);
    }

    @Override
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
