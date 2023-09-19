package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.BackpackFullReadDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.balcon.backpack.web.rest.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: 19.09.2023 Try to test without context startup 
@SpringBootTest
class BackpackMapperTest {
    @Autowired
    BackpackMapper mapper;

    @Test
    void checkFullMapping() {
        BackpackFullReadDto backpackDto = mapper.toFullReadDto(userBackpack2);
        int backpackWeight = userTent.getWeight() + userSleepingBag.getWeight() + userSleepingPad.getWeight();

        assertThat(backpackDto.id()).isEqualTo(USER_BACKPACK_2_ID);
        assertThat(backpackDto.equipment()).hasSize(userBackpack2.getEquipment().size());
        assertThat(backpackDto.weight()).isEqualTo(backpackWeight);
    }
}
