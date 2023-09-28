package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.UserRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
class UserServiceTest extends BaseMvcTest {
    private final UserService service;
    private final UserRepository repository;

    @Test
    void checkPasswordEncryption() {
        String email = "newuser@mail.ru";
        String password = "user_password";
        String name = "User name";
        UserCreateDto userCreateDto = new UserCreateDto(email, password, name);
        service.create(userCreateDto);
        User user = repository.findByEmail(email).orElseThrow();

        Assertions.assertThat(user.getPassword()).isNotEqualTo(password);
    }
}
