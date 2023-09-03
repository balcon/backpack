package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackDto;
import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.repository.BackpackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackpackService {
    private final BackpackRepository repository;

    public BackpackDto getById(int id) {
        // TODO: 03.09.2023 Check if not exists
        Backpack backpack = repository.findById(id).orElseThrow();
        int weight = backpack.getEquipment().stream()
                .mapToInt(Equipment::getWeight)
                .sum();
        return new BackpackDto(backpack.getName(), backpack.getEquipment(), weight);
    }
}
