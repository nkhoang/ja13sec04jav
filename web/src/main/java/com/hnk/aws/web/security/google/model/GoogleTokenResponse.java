package com.hnk.aws.web.security.google.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author hnguyen.
 */
public class GoogleTokenResponse {
    @JsonProperty(value = "access_token")
    public String accessToken;
    @JsonProperty(value = "refresh_token")
    public String refreshToken;
    @JsonProperty(value = "id_token")
    public String idToken;
    @JsonProperty(value = "expires_in")
    public Long expiresIn;
    @JsonProperty(value = "token_type")
    public String tokenType;

    public String toString() {
        return "\naccess_token: " + accessToken
                + "\nrefresh_token: " + refreshToken
                + "\nexpires_in: " + expiresIn
                + "\ntoken_type: " + tokenType
                + "\nid_token: " + idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
