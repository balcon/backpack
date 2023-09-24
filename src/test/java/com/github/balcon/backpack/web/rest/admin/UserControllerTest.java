package com.github.balcon.backpack.web.rest.admin;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.dto.mapper.UserMapper;
import com.github.balcon.backpack.model.Role;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.UserRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static com.github.balcon.backpack.web.rest.admin.UserController.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class UserControllerTest extends BaseMvcTest {
    private final UserMapper mapper;
    private final UserRepository repository;

    @Test
    void getAll() throws Exception {
        List<UserReadDto> usersReadDto = Stream.of(admin, user)
                .map(mapper::toReadDto)
                .toList();

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(usersReadDto)));
    }

    @Test
    void getById() throws Exception {
        UserReadDto userReadDto = mapper.toReadDto(user);

        mockMvc.perform(get(BASE_URL + "/" + USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(userReadDto)));
    }

    @Test
    void create() throws Exception {
        String mail = "new@mail.ru";
        String name = "New user";
        UserCreateDto userCreateDto = new UserCreateDto(mail, name);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(userCreateDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.role").value(Role.USER.name()));

        assertThat(repository.findByEmail(mail)).isPresent();
    }

    @Test
    void update() throws Exception {
        String name = "New name";
        Role role = Role.ADMIN;
        UserUpdateDto userUpdateDto = new UserUpdateDto(name, role);

        mockMvc.perform(put(BASE_URL + "/" + USER_ID)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(userUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.role").value(role.name()));

        User updatedUser = repository.findById(USER_ID).orElseThrow();

        assertThat(updatedUser.getRole()).isEqualTo(role);
        assertThat(updatedUser.getName()).isEqualTo(name);
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        repository.flush();

        assertThat(repository.findById(USER_ID)).isNotPresent();
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + DUMMY_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateNotFound() throws Exception {
        mockMvc.perform(put(BASE_URL + "/" + DUMMY_ID)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(new UserUpdateDto("New Name", Role.USER))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + DUMMY_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
