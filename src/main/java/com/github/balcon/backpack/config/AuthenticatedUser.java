package com.github.balcon.backpack.config;

import com.github.balcon.backpack.model.User;
import lombok.Getter;

import java.util.Collections;

@Getter
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {
    private final Integer id;

    private AuthenticatedUser(User user) {
        super(user.getEmail(), user.getPassword(), Collections.singleton(user.getRole()));
        this.id = user.getId();
    }

    public static AuthenticatedUser of(User user) {
        return new AuthenticatedUser(user);
    }
}
