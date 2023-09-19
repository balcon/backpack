package com.github.balcon.backpack.dto;

import java.util.List;

public record BackpackFullReadDto(int id,
                                  String name,
                                  int weight,
                                  List<EquipmentReadDto> equipment) {
}
