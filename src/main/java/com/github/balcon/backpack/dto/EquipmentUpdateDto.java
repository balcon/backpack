package com.github.balcon.backpack.dto;

import lombok.Builder;

@Builder
public record EquipmentUpdateDto(String name,
                                 String manufacturer,
                                 int weight) {
}
