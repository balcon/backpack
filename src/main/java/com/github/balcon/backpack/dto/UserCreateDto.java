package com.github.balcon.backpack.dto;

public record UserCreateDto(String email,
                            String password,
                            String name) {
}
