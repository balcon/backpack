package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.BackpackFullReadDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BackpackMapper {
    BackpackReadDto toReadDto(Backpack backpack);

    // Invoke #calcWeight()
    @Mapping(target = "weight", source = "backpack")
    BackpackFullReadDto toFullReadDto(Backpack backpack);

    Backpack toEntity(BackpackWriteDto backpackWriteDto);

    default Backpack toEntity(BackpackWriteDto backpackWriteDto, Backpack backpack) {
        backpack.setName(backpackWriteDto.name());
        return backpack;
    }

    default int calcWeight(Backpack backpack) {
        return backpack.getEquipment().stream()
                .mapToInt(Equipment::getWeight)
                .sum();
    }
}
