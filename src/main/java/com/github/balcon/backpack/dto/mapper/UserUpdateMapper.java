package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.model.User;

public class UserUpdateMapper implements Mapper<UserUpdateDto, User> {
    @Override
    public User map(UserUpdateDto user) {
        throw new UnsupportedOperationException();
    }

    // TODO: 13.09.2023 Do it better
    public User map(UserUpdateDto userDto, User user) {
        user.setName(userDto.name());
        user.setRole(userDto.role());
        return user;
    }
}
