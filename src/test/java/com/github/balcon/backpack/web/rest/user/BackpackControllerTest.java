package com.github.balcon.backpack.web.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.backpack.dto.BackpackCreateDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.mapper.BackpackDtoMapper;
import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.repository.BackpackRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import com.github.balcon.backpack.web.rest.util.MockAuthId;
import com.github.balcon.backpack.web.rest.util.MockAuthIdExtension;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static com.github.balcon.backpack.web.rest.user.BackpackController.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(MockAuthIdExtension.class)
class BackpackControllerTest extends BaseMvcTest {
    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final BackpackDtoMapper dtoMapper;
    private final BackpackRepository repository;

    @Test
    @MockAuthId(id = USER_ID)
    void getAllOfAuthUser() throws Exception {
        List<BackpackReadDto> usersBackpacks = Stream.of(userBackpack1, userBackpack2)
                .map(dtoMapper::toReadDto)
                .toList();

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(usersBackpacks)));
    }

    @Test
    @MockAuthId(id = USER_ID)
    void getById() throws Exception {
        BackpackReadDto backpack = dtoMapper.toReadDto(userBackpack1);

        mockMvc.perform(get(BASE_URL + "/" + USER_BACKPACK_1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(backpack)));
    }

    @Test
    @MockAuthId(id = ADMIN_ID)
    void create() throws Exception {
        String name = "New backpack";
        String backpackDto = jsonMapper.writeValueAsString(
                dtoMapper.toEntity(new BackpackCreateDto(name)));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(backpackDto))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(name));

        assertThat(repository.findAllByOwnerId(ADMIN_ID)).hasSize(2);
    }

    @Test
    @MockAuthId(id = USER_ID)
    void update() throws Exception {
        String name = "New name";

        Backpack backpack = dtoMapper.toEntity(new BackpackCreateDto(name));

        mockMvc.perform(put(BASE_URL + "/" + USER_BACKPACK_1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(backpack)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_BACKPACK_1_ID))
                .andExpect(jsonPath("$.name").value(name));

        assertThat(repository.findById(USER_BACKPACK_1_ID).orElseThrow().getName())
                .isEqualTo(name);
    }

    @Test
    @MockAuthId(id = USER_ID)
    void deleteById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + USER_TENT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        // TODO: 18.09.2023 Flush repo in service???
        repository.flush();

        assertThat(repository.findById(USER_TENT_ID)).isNotPresent();
    }
}
