package com.github.balcon.backpack.dto.person;

public record PersonReadDto(Integer id,
                            String email,
                            String name,
                            String role) {
}
