package com.switchfully.eurderproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@Service
public class SecurityService {
    private final Logger securityServiceLogger = LoggerFactory.getLogger(SecurityService.class);

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateAuthorization(String authorization, Feature feature) {
        UsernamePassword usernamePassword = getUsernamePassword(authorization);
        User user = userRepository.getUser(usernamePassword.getUsername());
        if (user == null) {
            securityServiceLogger.error("Unknown user" + usernamePassword.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unknown user" + usernamePassword.getUsername());
        }
        if (!user.doesPasswordMatch(usernamePassword.getPassword())) {
            securityServiceLogger.error("Password does not match for user" + usernamePassword.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid combination of user name and password");
        }
        if (!user.canHaveAccessTo(feature)) {
            securityServiceLogger.error("User " + usernamePassword.getUsername() + " does not have access to " + feature);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Action not allowed.");
        }
    }

    private UsernamePassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new UsernamePassword(username, password);
    }
}
