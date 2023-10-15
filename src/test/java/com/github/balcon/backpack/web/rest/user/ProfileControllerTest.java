package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateProfileDto;
import com.github.balcon.backpack.dto.mapper.UserMapper;
import com.github.balcon.backpack.model.Role;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.UserRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static com.github.balcon.backpack.web.rest.user.ProfileController.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@WithUserDetails(USER_EMAIL)
public class ProfileControllerTest extends BaseMvcTest {
    private final UserMapper mapper;
    private final UserRepository repository;

    @Test
    void getUserProfile() throws Exception {
        UserReadDto userReadDto = mapper.toReadDto(user);

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(userReadDto)));
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    void getAdminProfile() throws Exception {
        UserReadDto userReadDto = mapper.toReadDto(admin);

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(userReadDto)));
    }

    @Test
    @WithAnonymousUser
    void registration() throws Exception {
        String email = "new@mail.ru";
        String password = "password";
        String name = "New user";
        UserCreateDto userCreateDto = new UserCreateDto(email, password, name);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(userCreateDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.role").value(Role.USER.name()));

        assertThat(repository.findByEmail(email)).isPresent();
    }

    @Test
    void update() throws Exception {
        String name = "New name";
        UserUpdateProfileDto userUpdateProfileDto = UserUpdateProfileDto.builder()
                .name(name).build();

        mockMvc.perform(put(BASE_URL + "/" + USER_ID)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(userUpdateProfileDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.name").value(name));

        User updatedUser = repository.findById(USER_ID).orElseThrow();

        assertThat(updatedUser.getName()).isEqualTo(name);
    }
}
