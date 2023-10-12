package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.EquipmentFullReadDto;
import com.github.balcon.backpack.dto.EquipmentWriteDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.model.Equipment;
import org.mapstruct.Mapper;

@Mapper
public interface EquipmentMapper {
    EquipmentReadDto toReadDto(Equipment equipment);

    EquipmentFullReadDto toFullReadDto(Equipment equipment);

    Equipment toEntity(EquipmentWriteDto equipmentWriteDto);

    default Equipment toEntity(EquipmentWriteDto equipmentWriteDto, Equipment equipment) {
        equipment.setName(equipmentWriteDto.name());
        equipment.setManufacturer(equipmentWriteDto.manufacturer());
        equipment.setProperties(equipmentWriteDto.properties());
        equipment.setWeight(equipmentWriteDto.weight());
        return equipment;
    }
}
