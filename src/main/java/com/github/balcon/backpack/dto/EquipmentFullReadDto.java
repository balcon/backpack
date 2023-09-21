package com.github.balcon.backpack.dto;

import java.util.List;

public record EquipmentFullReadDto(int id,
                                   String name,
                                   String manufacturer,
                                   int weight,
                                   List<BackpackReadDto> backpacks) {
}
