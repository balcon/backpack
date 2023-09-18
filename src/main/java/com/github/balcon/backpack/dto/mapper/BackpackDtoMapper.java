package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.BackpackCreateDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.model.Backpack;
import org.mapstruct.Mapper;

@Mapper
public interface BackpackDtoMapper {
    BackpackReadDto toReadDto(Backpack backpack);

    Backpack toEntity(BackpackCreateDto backpackCreateDto);

    default Backpack toEntity(BackpackCreateDto backpackCreateDto, Backpack backpack){
        backpack.setName(backpackCreateDto.name());
        return backpack;
    }
}
