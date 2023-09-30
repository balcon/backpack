package com.github.balcon.backpack.dto;

import com.github.balcon.backpack.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(@NotBlank @Size(max = 64) String name,
                            @NotNull Role role) {
}
