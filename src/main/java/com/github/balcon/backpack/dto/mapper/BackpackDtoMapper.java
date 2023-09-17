package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.model.Backpack;
import org.mapstruct.Mapper;

@Mapper
public interface BackpackDtoMapper {
    BackpackReadDto toReadDto(Backpack backpack);
}
