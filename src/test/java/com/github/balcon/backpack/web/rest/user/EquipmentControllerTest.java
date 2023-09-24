package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.dto.EquipmentFullReadDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentWriteDto;
import com.github.balcon.backpack.dto.mapper.EquipmentMapper;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.repository.EquipmentRepository;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static com.github.balcon.backpack.web.rest.user.EquipmentController.BASE_URL;
import static com.github.balcon.backpack.web.rest.user.EquipmentController.COLLECTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class EquipmentControllerTest extends BaseMvcTest {
    public static final EquipmentWriteDto writeDtoDummy = new EquipmentWriteDto("Dummy", null, 0);

    private final EquipmentMapper mapper;
    private final EquipmentRepository repository;

    @Test
    void getAllOfAuthUser() throws Exception {
        List<EquipmentReadDto> equipmentReadDto = Stream.of(userTent, userSleepingBag, userSleepingPad)
                .map(mapper::toReadDto)
                .toList();

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(equipmentReadDto)));
    }

    @Test
    void getById() throws Exception {
        EquipmentFullReadDto equipmentFullReadDto =
                mapper.toFullReadDto(userSleepingBag.toBuilder()
                        .backpacks(List.of(userBackpack1, userBackpack2)).build());

        mockMvc.perform(get(BASE_URL + "/" + USER_SLEEPING_BAG_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(equipmentFullReadDto)))
                .andExpect(jsonPath("$.backpacks", hasSize(2)));
    }

    @Test
    void create() throws Exception {
        String name = "Star River 2";
        String manufacturer = "Naturehike";
        int weight = 2000;
        EquipmentWriteDto equipmentWriteDto = new EquipmentWriteDto(name, manufacturer, weight);
        int equipmentCount = repository.findAllByOwnerId(USER_ID).size();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(equipmentWriteDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.manufacturer").value(manufacturer))
                .andExpect(jsonPath("$.weight").value(weight));

        assertThat(repository.findAllByOwnerId(USER_ID)).hasSize(equipmentCount + 1);
    }

    @Test
    void update() throws Exception {
        String name = "New name";
        String manufacturer = "New Manufacturer";
        int weight = 1000;
        EquipmentWriteDto equipmentWriteDto = new EquipmentWriteDto(name, manufacturer, weight);

        mockMvc.perform(put(BASE_URL + "/" + USER_TENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(equipmentWriteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_TENT_ID))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.manufacturer").value(manufacturer));

        Equipment updatedEquipment = repository.findById(USER_TENT_ID).orElseThrow();

        assertThat(updatedEquipment.getName()).isEqualTo(name);
        assertThat(updatedEquipment.getManufacturer()).isEqualTo(manufacturer);
        assertThat(updatedEquipment.getWeight()).isEqualTo(weight);
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + USER_TENT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        repository.flush();

        assertThat(repository.findById(USER_TENT_ID)).isNotPresent();
    }

    @Test
    void addBackpack() throws Exception {
        EquipmentFullReadDto equipmentFullReadDto = mapper.toFullReadDto(userTent.toBuilder()
                .backpacks(List.of(userBackpack1, userBackpack2)).build());
        int backpackCount = repository.findById(USER_TENT_ID).orElseThrow().getBackpacks().size();

        mockMvc.perform(post(BASE_URL + "/" + USER_TENT_ID + COLLECTION + "/" + USER_BACKPACK_1_ID))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(toJson(equipmentFullReadDto)));

        assertThat(repository.findById(USER_SLEEPING_BAG_ID).orElseThrow().getBackpacks().size()).isEqualTo(backpackCount + 1);
    }

    @Test
    void removeBackpack() throws Exception {
        EquipmentFullReadDto equipmentFullReadDto = mapper.toFullReadDto(userSleepingBag.toBuilder()
                .backpacks(List.of(userBackpack1)).build());
        int backpackCount = repository.findById(USER_SLEEPING_BAG_ID).orElseThrow().getBackpacks().size();

        mockMvc.perform(delete(BASE_URL + "/" + USER_SLEEPING_BAG_ID + COLLECTION + "/" + USER_BACKPACK_2_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(equipmentFullReadDto)));

        assertThat(repository.findById(USER_SLEEPING_BAG_ID).orElseThrow().getBackpacks().size()).isEqualTo(backpackCount - 1);
    }

    @Test
    void getNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + DUMMY_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotOwner() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + ADMIN_TENT_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateNotFound() throws Exception {
        mockMvc.perform(put(BASE_URL + "/" + DUMMY_ID)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(writeDtoDummy)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateNotOwner() throws Exception {
        mockMvc.perform(put(BASE_URL + "/" + ADMIN_TENT_ID)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(writeDtoDummy)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + DUMMY_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotOwner() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + ADMIN_TENT_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addBackpackNotFound() throws Exception {
        mockMvc.perform(post(BASE_URL + "/" + USER_TENT_ID + COLLECTION + "/" + DUMMY_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addBackpackNotOwner() throws Exception {
        mockMvc.perform(post(BASE_URL + "/" + USER_TENT_ID + COLLECTION + "/" + ADMIN_BACKPACK_1_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
