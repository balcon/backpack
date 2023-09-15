package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {
    UserReadDto toReadDto(User user);

    User toUser(UserCreateDto userCreateDto);

    default User toUser(UserUpdateDto userUpdateDto, User user) {
        user.setName(userUpdateDto.name());
        user.setRole(userUpdateDto.role());
        return user;
    }
}
