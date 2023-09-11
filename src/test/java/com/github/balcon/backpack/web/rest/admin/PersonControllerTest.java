package com.github.balcon.backpack.web.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.backpack.dto.person.PersonCreateDto;
import com.github.balcon.backpack.dto.person.PersonReadDto;
import com.github.balcon.backpack.dto.mapper.person.PersonReadDtoMapper;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import com.github.balcon.backpack.web.rest.TestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.admin.PersonController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class PersonControllerTest extends BaseMvcTest {
    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final PersonReadDtoMapper dtoMapper;

    @Test
    void getAll() throws Exception {
        List<PersonReadDto> persons = Stream.of(TestData.admin, TestData.user)
                .map(dtoMapper::map)
                .toList();

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(persons)));
    }

    @Test
    void getById() throws Exception {
        PersonReadDto user = dtoMapper.map(TestData.user);

        mockMvc.perform(get(BASE_URL + "/" + TestData.USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(user)));
    }

    @Test
    void create() throws Exception {
        String newUserJson = jsonMapper.writeValueAsString(PersonCreateDto.builder()
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
}
