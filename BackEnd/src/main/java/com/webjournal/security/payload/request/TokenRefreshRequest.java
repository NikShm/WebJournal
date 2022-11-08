package com.webjournal.security.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class TokenRefreshRequest
 * @since 11/8/2022 - 17.57
 **/
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;

    public TokenRefreshRequest() {
    }

    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "TokenRefreshRequest{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
