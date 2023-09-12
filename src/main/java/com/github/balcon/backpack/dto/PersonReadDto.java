package com.github.balcon.backpack.dto;

public record PersonReadDto(Integer id,
                            String email,
                            String name,
                            String role) {
}
