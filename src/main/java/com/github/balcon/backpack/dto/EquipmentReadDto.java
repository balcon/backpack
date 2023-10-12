package com.github.balcon.backpack.dto;

import java.util.Map;

public record EquipmentReadDto(int id,
                               String name,
                               String manufacturer,
                               Map<String, String> properties,
                               int weight) {
}
