package com.github.balcon.backpack.web.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.backpack.dto.EquipmentCreateDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentUpdateDto;
import com.github.balcon.backpack.dto.mapper.EquipmentDtoMapper;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.repository.EquipmentRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import com.github.balcon.backpack.web.rest.util.MockAuthId;
import com.github.balcon.backpack.web.rest.util.MockAuthIdExtension;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static com.github.balcon.backpack.web.rest.user.EquipmentController.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(MockAuthIdExtension.class)
class EquipmentControllerTest extends BaseMvcTest {
    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final EquipmentDtoMapper dtoMapper;
    private final EquipmentRepository repository;

    @Test
    @MockAuthId(id = USER_ID)
    void getAllOfAuthUser() throws Exception {
        List<EquipmentReadDto> equipment = Stream.of(userTent, userSleepingBag, userSleepingPad)
                .map(dtoMapper::toReadDto)
                .toList();

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(equipment)));
    }

    @Test
    @MockAuthId(id = USER_ID)
    void getById() throws Exception {
        EquipmentReadDto equipment = dtoMapper.toReadDto(userTent);

        mockMvc.perform(get(BASE_URL + "/" + USER_TENT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(equipment)));
    }

    @Test
    @Disabled("Check return 403 Forbidden or 404 Not found")
    void getByIdIfNotOwner() {
    }

    @Test
    @MockAuthId(id = USER_ID)
    void create() throws Exception {
        String name = "Star River 2";
        String manufacturer = "Naturehike";
        int weight = 2000;
        String equipmentJson = jsonMapper.writeValueAsString(
                new EquipmentCreateDto(name, manufacturer, weight));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(equipmentJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.manufacturer").value(manufacturer))
                .andExpect(jsonPath("$.weight").value(weight));

        assertThat(repository.findAllByOwnerId(USER_ID)).hasSize(4);
    }

    @Test
    @MockAuthId(id = USER_ID)
    void update() throws Exception {
        String name = "New name";
        String manufacturer = "New Manufacturer";
        String updateEquipmentJson = jsonMapper.writeValueAsString(
                EquipmentUpdateDto.builder()
                        .name(name)
                        .manufacturer(manufacturer).build());

        mockMvc.perform(put(BASE_URL + "/" + USER_TENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEquipmentJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_TENT_ID))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.manufacturer").value(manufacturer));

        Equipment updatedEquipment = repository.findById(USER_TENT_ID).orElseThrow();

        assertThat(updatedEquipment.getName()).isEqualTo(name);
        assertThat(updatedEquipment.getManufacturer()).isEqualTo(manufacturer);
    }

    @Test
    @MockAuthId(id = USER_ID)
    void deleteById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + USER_TENT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        repository.flush();

        assertThat(repository.findById(USER_TENT_ID)).isNotPresent();
    }
}
