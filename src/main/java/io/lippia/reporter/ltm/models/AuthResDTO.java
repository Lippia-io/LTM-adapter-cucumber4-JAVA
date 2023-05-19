package io.lippia.reporter.ltm.models;

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

    private String accessToken;

    private int expiresIn;

    private int refreshExpiresIn;

    private String refreshToken;

    private String tokenType;

    private int notBeforePolicy;

    private String sessionState;

    private String scope;
}