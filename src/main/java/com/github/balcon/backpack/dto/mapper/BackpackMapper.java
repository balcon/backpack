package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.model.Backpack;
import org.mapstruct.Mapper;

@Mapper
public interface BackpackMapper {
    BackpackReadDto toReadDto(Backpack backpack);

    Backpack toEntity(BackpackWriteDto backpackWriteDto);

    default Backpack toEntity(BackpackWriteDto backpackWriteDto, Backpack backpack){
        backpack.setName(backpackWriteDto.name());
        return backpack;
    }
}
