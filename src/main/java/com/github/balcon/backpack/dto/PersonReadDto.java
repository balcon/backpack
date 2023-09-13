package com.github.balcon.backpack.dto;

import com.github.balcon.backpack.model.Role;

public record PersonReadDto(Integer id,
                            String email,
                            String name,
                            Role role) {
}
