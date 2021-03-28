package com.depanker.ticker.security.constants;

/**
 * Created by rahul.yadav1 on 15/02/18.
 * these are all constants related to spring security and jwt integration
 */
public interface SecurityConstants {
    //long EXPIRATIONTIME = 864_000_000; // 10 days
    String SECRET = "CRICTIL";
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
    String OTP_VERIFY_URL = "/otp/verify";
    String OAUTH_LOGIN_TRUE_CALLER = "/oauth/login/true-caller";
    String OAUTH2_LOGIN_GOOGLE = "/oauth2/login/google";
    String OAUTH2_LOGIN_FACEBOOK = "/oauth2/login/facebook";
    String OTP_URL = "/otp";
    String OTP_V2_URL = "/otp/v2";
    String USER_PROFILE_REFERRAL_URL = "/userprofile/referral/*";
    String BOT_INJECT_USER_URL = "/bot/inject-user";
    String ADMIN_DELETE_USER_PIC_URL = "/admin/delete-user-pic";
    String CACHE_TRUE_CALLER_KEY = "/admin/cache-true-caller-key";
    String CLEAR_USER_CACHE_KEY = "/admin/clear-user-cache-key";
    String USER_SESSION_ACCESS_TOKEN = "/user/session/access-token";


    long TEMP_EXPIRATIONTIME = 300_000; // 5 minutes

    String TEMP_SECRET = "$i$T%l^$Tr0&Gi&dI@";

    //String TEMP_TOKEN_PREFIX = "Temp";

    String TEMP_HEADER_STRING = "TEMP_TOKEN";


}
