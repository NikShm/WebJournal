package com.webjournal.service.refreshtoken;

import com.webjournal.entity.RefreshToken;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class RefreshTokenService
 * @since 11/8/2022 - 16.56
 **/
public interface RefreshTokenService {
    RefreshToken getByToken(String token);
    RefreshToken create(Integer userId);
    RefreshToken verifyExpiration(String token);
}
