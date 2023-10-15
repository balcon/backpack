package com.github.balcon.backpack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserCreateDto(@NotBlank @Email String email,
                            @NotBlank @Size(min = 8, max = 64) String password,
                            @NotBlank @Size(max = 64) String name) {
}
