package com.github.balcon.backpack.dto;

import com.github.balcon.backpack.model.Equipment;

import java.util.List;

public record BackpackDto(String name,
                          List<Equipment> equipment,
                          int weight) {
}