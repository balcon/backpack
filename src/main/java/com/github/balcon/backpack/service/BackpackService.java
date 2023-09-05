package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackDto;
import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.model.Part;
import com.github.balcon.backpack.repository.BackpackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BackpackService {
    private final BackpackRepository repository;

    public BackpackDto getById(int id) {
        // TODO: 03.09.2023 Check if not exists
        Backpack backpack = repository.findById(id).orElseThrow();
        int weight;

        // If equipment doesn't contain any parts, then weight of equipment summed
        weight = backpack.getEquipment().stream()
                .filter(e -> e.getParts().isEmpty())
                .mapToInt(Equipment::getWeight)
                .sum();

        // If equipment contains some parts, then summary weight of parts summed
        weight += backpack.getEquipment().stream()
                .filter(e -> !e.getParts().isEmpty())
                .flatMapToInt(e -> e.getParts().stream()
                        .mapToInt(Part::getWeight))
                .sum();

        return new BackpackDto(backpack.getName(), backpack.getEquipment(), weight);
    }

    public void save(Backpack backpack) {
        // TODO: 05.09.2023 Check is not new
        repository.save(backpack);
    }

    // TODO: 05.09.2023 return DTO
    public List<Backpack> getAll() {
        return repository.findAll();
    }
}
