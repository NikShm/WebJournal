package com.webjournal.repository;

import com.webjournal.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class RefreshTokenRepository
 * @since 11/8/2022 - 16.50
 **/
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
}
