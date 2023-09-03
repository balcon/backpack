package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackDto;
import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.repository.BackpackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BackpackServiceTest {

    @Mock
    private BackpackRepository repository;

    @InjectMocks
    private BackpackService service;

    @Test
    void calcWeight() {
        Equipment tent = Equipment.builder()
                .name("Tent")
                .manufacturer("MSR")
                .weight(2500).build();
        Equipment sleepingBag = Equipment.builder()
                .name("Arctic +5")
                .manufacturer("Red Fox")
                .weight(1500).build();

        Backpack backpack = new Backpack("BackPack 1", List.of(tent, sleepingBag));

        doReturn(Optional.of(backpack))
                .when(repository)
                .findById(1);

        BackpackDto backpackDto = service.getById(1);

        assertThat(backpackDto.weight()).isEqualTo(4000);
    }
}