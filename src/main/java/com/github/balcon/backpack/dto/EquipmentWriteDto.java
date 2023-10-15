package com.github.balcon.backpack.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public record EquipmentWriteDto(@NotBlank @Size(max = 64) String name,
                                @Size(max = 64) String manufacturer,
                                Map<@NotBlank @Size(max = 64) String, @NotBlank @Size(max = 64) String> properties,
                                @Min(0) int weight) {

    @SuppressWarnings("all")
    public static class EquipmentWriteDtoBuilder {
        private Map<String, String> properties = new HashMap<>();
    }
}
