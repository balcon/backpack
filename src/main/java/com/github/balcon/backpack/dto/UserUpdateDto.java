package com.github.balcon.backpack.dto;

import com.github.balcon.backpack.model.Role;
import lombok.Builder;

@Builder
public record UserUpdateDto(String name,
                            Role role) {
}
