package com.github.balcon.backpack.dto;

import com.github.balcon.backpack.model.Role;

public record UserReadDto(Integer id,
                          String email,
                          String name,
                          Role role) {
}
