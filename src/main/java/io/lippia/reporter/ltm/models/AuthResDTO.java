package io.lippia.reporter.ltm.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResDTO {
    public AuthResDTO() {
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setRefreshExpiresIn(int refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public int getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setNotBeforePolicy(int notBeforePolicy) {
        this.notBeforePolicy = notBeforePolicy;
    }

    public int getNotBeforePolicy() {
        return notBeforePolicy;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_expires_in")
    private int refreshExpiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("not-before-policy")
    private int notBeforePolicy;

    @JsonProperty("session_state")
    private String sessionState;

    @JsonProperty("scope")
    private String scope;

}
