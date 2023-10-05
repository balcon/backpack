package com.github.balcon.backpack.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final int id;

    @Override
    public String toString() {
        return "Resource [%s] with id [%d] not found".formatted(resource, id);
    }
}
