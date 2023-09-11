package com.github.balcon.backpack.web.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.backpack.dto.PersonReadDto;
import com.github.balcon.backpack.dto.mapper.PersonReadDtoMapper;
import com.github.balcon.backpack.web.rest.BaseIntegrationTest;
import com.github.balcon.backpack.web.rest.TestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RequiredArgsConstructor
@Sql("classpath:data.sql")
class PersonControllerTest extends BaseIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final PersonReadDtoMapper dtoMapper;

    @Test
    void getAll() throws Exception {
        List<PersonReadDto> persons = Stream.of(TestData.admin, TestData.user)
                .map(dtoMapper::map)
                .toList();

        mockMvc.perform(get(PersonController.BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(persons)));
    }

    @Test
    void getById() throws Exception {
        PersonReadDto user = dtoMapper.map(TestData.user);
        mockMvc.perform(get(PersonController.BASE_URL + "/" + TestData.USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(user)));
    }
}
