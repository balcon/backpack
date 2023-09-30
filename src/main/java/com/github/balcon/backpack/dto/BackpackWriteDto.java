package com.github.balcon.backpack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BackpackWriteDto(@NotBlank @Size(max = 64) String name) {
}
