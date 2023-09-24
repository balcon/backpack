package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.dto.BackpackFullReadDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.dto.mapper.BackpackMapper;
import com.github.balcon.backpack.repository.BackpackRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import com.github.balcon.backpack.web.rest.util.MockAuthId;
import com.github.balcon.backpack.web.rest.util.MockAuthIdExtension;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static com.github.balcon.backpack.web.rest.user.BackpackController.BASE_URL;
import static com.github.balcon.backpack.web.rest.user.BackpackController.COLLECTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@ExtendWith(MockAuthIdExtension.class)
class BackpackControllerTest extends BaseMvcTest {
    private final BackpackMapper mapper;
    private final BackpackRepository repository;

    @Test
    @MockAuthId(id = USER_ID)
    void getAllOfAuthUser() throws Exception {
        List<BackpackReadDto> backpacksReadDto = Stream.of(userBackpack1, userBackpack2)
                .map(mapper::toReadDto)
                .toList();

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(backpacksReadDto)));
    }

    @Test
    @MockAuthId(id = USER_ID)
    void getById() throws Exception {
        BackpackFullReadDto backpackFullReadDto = mapper.toFullReadDto(userBackpack1);

        mockMvc.perform(get(BASE_URL + "/" + USER_BACKPACK_1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(backpackFullReadDto)))
                .andExpect(jsonPath("$.weight").value(userSleepingBag.getWeight() + userSleepingPad.getWeight()))
                .andExpect(jsonPath("$.equipment", hasSize(userBackpack1.getEquipment().size())));
    }

    @Test
    @MockAuthId(id = ADMIN_ID)
    void create() throws Exception {
        String name = "New backpack";
        BackpackWriteDto backpackWriteDto = new BackpackWriteDto(name);
        int backpacksCount = repository.findAllByOwnerId(ADMIN_ID).size();

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(backpackWriteDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(name));

        assertThat(repository.findAllByOwnerId(ADMIN_ID)).hasSize(backpacksCount + 1);
    }

    @Test
    @MockAuthId(id = USER_ID)
    void update() throws Exception {
        String name = "New name";
        BackpackWriteDto backpackWriteDto = new BackpackWriteDto(name);

        mockMvc.perform(put(BASE_URL + "/" + USER_BACKPACK_1_ID)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(backpackWriteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_BACKPACK_1_ID))
                .andExpect(jsonPath("$.name").value(name));

        assertThat(repository.findById(USER_BACKPACK_1_ID).orElseThrow().getName()).isEqualTo(name);
    }

    @Test
    @MockAuthId(id = USER_ID)
    void deleteById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + USER_BACKPACK_1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        // TODO: 18.09.2023 Flush repo in service???
        repository.flush();

        assertThat(repository.findById(USER_BACKPACK_1_ID)).isNotPresent();
    }

    @Test
    @MockAuthId(id = USER_ID)
    void addEquipmentToBackpack() throws Exception {
        BackpackFullReadDto backpackFullReadDto = mapper.toFullReadDto(userBackpack1.toBuilder()
                .equipment(List.of(userSleepingBag, userSleepingPad, userTent)).build());
        int equipmentCount = repository.findById(USER_BACKPACK_1_ID).orElseThrow().getEquipment().size();

        mockMvc.perform(post(BASE_URL + "/" + USER_BACKPACK_1_ID + COLLECTION + "/" + USER_TENT_ID))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(toJson(backpackFullReadDto)));

        assertThat(repository.findById(USER_BACKPACK_1_ID).orElseThrow().getEquipment()).hasSize(equipmentCount + 1);
    }

    @Test
    @MockAuthId(id = USER_ID)
    void removeEquipmentFromBackpack() throws Exception {
        BackpackFullReadDto backpackFullReadDto = mapper.toFullReadDto(userBackpack1.toBuilder()
                .equipment(List.of(userSleepingBag)).build());
        int equipmentCount = repository.findById(USER_BACKPACK_1_ID).orElseThrow().getEquipment().size();

        mockMvc.perform(delete(BASE_URL + "/" + USER_BACKPACK_1_ID + COLLECTION + "/" + USER_SLEEPING_PAD_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(backpackFullReadDto)));

        assertThat(repository.findById(USER_BACKPACK_1_ID).orElseThrow().getEquipment())
                .hasSize(equipmentCount - 1);
    }
}
