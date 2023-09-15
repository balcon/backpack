package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.model.Equipment;
import org.mapstruct.Mapper;

@Mapper
public interface EquipmentDtoMapper {
    EquipmentReadDto toReadDto(Equipment equipment);
}
