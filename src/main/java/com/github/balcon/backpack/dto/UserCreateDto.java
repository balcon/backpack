package com.github.balcon.backpack.dto;

import lombok.Builder;

@Builder
public record UserCreateDto(String email,
                            String name) {
}
