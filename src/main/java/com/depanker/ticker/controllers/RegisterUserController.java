package com.depanker.ticker.controllers;

import com.depanker.ticker.beans.RegisterUserResponse;
import com.depanker.ticker.security.bean.TickerUser;
import com.depanker.ticker.security.config.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class RegisterUserController {
    private final UserDetailsServiceImpl userDetailsService;
    @PostMapping("/register-user")
    public RegisterUserResponse saveUser(@Valid @RequestBody TickerUser tickerUser) {
        userDetailsService.save(tickerUser);
        return new RegisterUserResponse(String.format("User %s has been register", tickerUser.getUsername()));
    }
}
