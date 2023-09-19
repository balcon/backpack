package com.github.balcon.backpack.dto;

import com.github.balcon.backpack.model.Role;

public record UserUpdateDto(String name,
                            Role role) {
}
