package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.EquipmentCreateDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentUpdateDto;
import com.github.balcon.backpack.model.Equipment;
import org.mapstruct.Mapper;

@Mapper
public interface EquipmentDtoMapper {
    EquipmentReadDto toReadDto(Equipment equipment);

    Equipment toEntity(EquipmentCreateDto equipmentCreateDto);

    default Equipment toEntity(EquipmentUpdateDto equipmentUpdateDto, Equipment equipment) {
        equipment.setName(equipmentUpdateDto.name());
        equipment.setManufacturer(equipmentUpdateDto.manufacturer());
        equipment.setWeight(equipmentUpdateDto.weight());
        return equipment;
    }
}
