package com.webjournal.service.refreshtoken;

import com.webjournal.entity.RefreshToken;
import com.webjournal.entity.User;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.exception.TokenRefreshException;
import com.webjournal.repository.RefreshTokenRepository;
import com.webjournal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class RefreshTokenServiceImpl
 * @since 11/8/2022 - 16.59
 **/
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${application.jwt.refreshToken.validity.in.days}")
    private Integer tokenValidityInDays;

    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshToken getByToken(String token) {
        return repository.findByToken(token).orElseThrow(() -> new TokenRefreshException(token, "Refresh token was not found in database"));
    }

    @Override
    public RefreshToken create(Integer userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + userId)));
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(tokenValidityInDays));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = repository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(String token) {
        RefreshToken refreshToken = getByToken(token);
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            repository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token is expired. Please make a new login request");
        }
        return refreshToken;
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        repository.deleteByUser(user);
    }
}
