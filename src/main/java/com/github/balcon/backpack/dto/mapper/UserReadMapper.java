package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.model.User;

public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User user) {
        return new UserReadDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole());
    }
}
