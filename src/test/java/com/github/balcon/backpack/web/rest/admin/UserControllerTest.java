package com.github.balcon.backpack.web.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.dto.mapper.UserReadMapper;
import com.github.balcon.backpack.model.Role;
import com.github.balcon.backpack.repository.UserRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import com.github.balcon.backpack.web.rest.TestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.admin.UserController.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RequiredArgsConstructor
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE))
class UserControllerTest extends BaseMvcTest {
    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final UserReadMapper dtoMapper;
    private final UserRepository repository;

    @Test
    void getAll() throws Exception {
        List<UserReadDto> users = Stream.of(TestData.admin, TestData.user)
                .map(dtoMapper::map)
                .toList();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(users)));
    }

    @Test
    void get() throws Exception {
        UserReadDto user = dtoMapper.map(TestData.user);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + TestData.USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(user)));
    }

    @Test
    void create() throws Exception {
        String newUserJson = jsonMapper.writeValueAsString(
                UserCreateDto.builder()
                        .email("new@mail.ru")
                        .name("New user").build());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    void update() throws Exception {
        String updatedUserJson = jsonMapper.writeValueAsString(
                UserUpdateDto.builder()
                        .name("New name")
                        .role(Role.ADMIN).build());

        mockMvc.perform(put(BASE_URL + "/" + TestData.USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.USER_ID))
                .andExpect(jsonPath("$.role").value(Role.ADMIN.name()));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + TestData.USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        repository.flush();

        assertThat(repository.findById(TestData.USER_ID)).isNotPresent();
    }
}
