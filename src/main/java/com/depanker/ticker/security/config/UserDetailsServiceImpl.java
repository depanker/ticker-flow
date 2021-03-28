package com.depanker.ticker.security.config;

import com.depanker.ticker.security.bean.TickerUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahul.yadav1 on 16/02/18.
 * This service will be used to load the user via otp, otp token and mobile(used as user name)
 */

@Service("applicationUserDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Map<String, TickerUser> USER_BY_USERNAME = new HashMap<>();


    @PostConstruct
    public void setupDefaultUser() {
        USER_BY_USERNAME.put("depanker", new TickerUser("depanker", "depanker"));
    }

    public TickerUser save(TickerUser tickerUser) {
        USER_BY_USERNAME.putIfAbsent(tickerUser.getUsername(), tickerUser);
        return tickerUser;
    }
    /**
     * load user by username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TickerUser user = USER_BY_USERNAME.get(username);
        String mobile = user.getUsername();
        String password = user.getPassword();
        return new User(mobile, password, Collections.emptyList());
    }
}