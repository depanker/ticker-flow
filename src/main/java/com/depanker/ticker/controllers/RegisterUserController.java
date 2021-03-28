package com.depanker.ticker.controllers;

import com.depanker.ticker.security.bean.TickerUser;
import com.depanker.ticker.security.config.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterUserController {
    private final UserDetailsServiceImpl userDetailsService;
    @PostMapping("/register")
    public void saveUser(@RequestBody TickerUser tickerUser) {
        userDetailsService.save(tickerUser);
    }
}
