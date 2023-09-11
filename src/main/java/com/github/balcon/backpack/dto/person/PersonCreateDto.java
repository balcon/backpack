package com.github.balcon.backpack.dto.person;

import lombok.Builder;

@Builder
public record PersonCreateDto(String email,
                              String name) {
}
