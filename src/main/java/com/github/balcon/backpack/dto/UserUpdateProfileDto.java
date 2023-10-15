package com.github.balcon.backpack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateProfileDto(@NotBlank @Size(max = 64) String name) {
}
