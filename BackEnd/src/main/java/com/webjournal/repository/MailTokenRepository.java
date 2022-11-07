package com.webjournal.repository;

import com.webjournal.entity.MailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class MailTokenRepository
 * @since 11/1/2022 - 21.32
 **/
@Repository
public interface MailTokenRepository extends JpaRepository<MailToken, Integer> {
    Optional<MailToken> findByToken(String token);
}
