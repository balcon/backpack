package com.github.balcon.backpack.dto;

import lombok.Builder;

@Builder
public record PersonCreateDto(String email,
                              String name) {
}
