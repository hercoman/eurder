package com.switchfully.eurderproject.security;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.switchfully.eurderproject.security.Role.*;

@Repository
public class UserRepository {

    private final Map<String, User> userMap = ImmutableMap.<String, User>builder()
            .put("Herbert", new User("Herbert", "Switch1", ADMIN))
            .put("Tim", new User("Tim", "pwd", CUSTOMER))
            .build();

    public User getUser(String userName) {
        return userMap.get(userName);
    }
}
