package com.depanker.ticker.security.config;

import com.depanker.ticker.security.bean.TickerUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


@Slf4j
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private String secret;
    /**
     * configured by Web Security Config
     *
     * @param url
     * @param authManager
     */
    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    /**
     * This method is for attempting authentication by credentials provided by user
     *
     * @param req
     * @param res
     * @return Authentication
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        /* get otp credentials from login request */
        String url = req.getRequestURL().toString();
        TickerUser credentials = new ObjectMapper()//UserDOA {}
                .readValue(req.getInputStream(), TickerUser.class);
        /* authenticates user */
        return authenticate(credentials.getUsername(), credentials.getPassword());
    }

    private Authentication authenticate(String mobile, String password) {//Got from request
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        mobile,
                        password,
                        Collections.emptyList()
                )
        );
    }


    /**
     * This method will be called if authentication is success
     *
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        TokenAuthenticationService
                .addAuthentication(req, res, auth.getName(), url);
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
