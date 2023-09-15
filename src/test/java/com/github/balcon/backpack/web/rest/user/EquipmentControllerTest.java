package com.github.balcon.backpack.web.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.mapper.EquipmentDtoMapper;
import com.github.balcon.backpack.web.rest.BaseMvcTest;
import com.github.balcon.backpack.web.rest.util.MockAuthId;
import com.github.balcon.backpack.web.rest.util.MockAuthIdExtension;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static com.github.balcon.backpack.web.rest.user.EquipmentController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(MockAuthIdExtension.class)
class EquipmentControllerTest extends BaseMvcTest {
    private final MockMvc mockMvc;
    private final ObjectMapper jsonMapper;
    private final EquipmentDtoMapper dtoMapper;

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
    void getById() throws Exception {
        EquipmentReadDto equipment = dtoMapper.toReadDto(userTent);

        mockMvc.perform(get(BASE_URL + "/" + USER_TENT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(equipment)));
    }

    @Test
    @Disabled("Check return 403 Forbidden")
    void getByIdIfNotOwner() {
    }
}
