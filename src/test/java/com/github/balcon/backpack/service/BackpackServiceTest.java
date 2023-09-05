package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackDto;
import com.github.balcon.backpack.repository.BackpackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.balcon.backpack.web.rest.TestData.*;
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
        doReturn(Optional.of(backpack1))
                .when(repository)
                .findById(1);

        BackpackDto backpackDto = service.getById(1);

        assertThat(backpackDto.weight()).isEqualTo(2000);
    }

    @Test
    void calcWeightWithParts() {
        doReturn(Optional.of(backpack2))
                .when(repository)
                .findById(1);

        BackpackDto backpackDto = service.getById(1);

        assertThat(backpackDto.weight()).isEqualTo(3400);
    }
}
