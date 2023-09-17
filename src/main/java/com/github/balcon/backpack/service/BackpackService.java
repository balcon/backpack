package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.mapper.BackpackDtoMapper;
import com.github.balcon.backpack.repository.BackpackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BackpackService {
    private final BackpackRepository repository;
    private final BackpackDtoMapper dtoMapper;

    // TODO: 17.09.2023 check if not owner
    public Optional<BackpackReadDto> get(int id, int userId) {
        return repository.findById(id)
                .map(dtoMapper::toReadDto);
    }

    public List<BackpackReadDto> getAllByUser(int userId) {
        return repository.findAllByOwnerId(userId).stream()
                .map(dtoMapper::toReadDto)
                .toList();
    }
}
