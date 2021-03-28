package com.depanker.ticker.repos;

import com.depanker.ticker.beans.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DummyUserRepository {
    private static Map<String, UserDTO> usersByName = new HashMap<>();
    public UserDTO save(UserDTO user) {
        usersByName.putIfAbsent(user.getUsername(), user);
        return user;
    }

    public UserDTO findUserByUserName(String userName) {
        return usersByName.get(userName);
    }
}
