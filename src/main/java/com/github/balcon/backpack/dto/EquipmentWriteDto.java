package com.github.balcon.backpack.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipmentWriteDto(@NotBlank @Size(max = 64) String name,
                                @Size(max = 64) String manufacturer,
                                @Min(0) int weight) {
}
