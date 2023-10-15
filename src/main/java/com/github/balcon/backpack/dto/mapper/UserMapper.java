package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.dto.UserUpdateProfileDto;
import com.github.balcon.backpack.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserReadDto toReadDto(User user);

    User toEntity(UserCreateDto userCreateDto);

    default User toEntity(UserUpdateDto userUpdateDto, User user) {
        user.setName(userUpdateDto.name());
        user.setRole(userUpdateDto.role());
        return user;
    }

    default User toEntity(UserUpdateProfileDto userUpdateProfileDto, User user) {
        user.setName(userUpdateProfileDto.name());
        return user;
    }
}
