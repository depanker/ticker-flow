package com.depanker.ticker.security.constants;

/**
 * these are all constants related to spring security and jwt integration
 */
public interface SecurityConstants {
    String SECRET = "SOLACTIVE";
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
    String TOKEN_URL = "/token";
    String REGISTER_USER = "/register-user";
}
