package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.model.User;

public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    @Override
    public User map(UserCreateDto user) {
        return User.builder()
                .email(user.email())
                .name(user.name()).build();
    }
}
