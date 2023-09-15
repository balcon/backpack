package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.mapper.EquipmentDtoMapper;
import com.github.balcon.backpack.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipmentService {
    private final EquipmentRepository repository;
    private final EquipmentDtoMapper dtoMapper;

    public List<EquipmentReadDto> getAllByUser(int authUserId) {
        return repository.findAllByOwnerId(authUserId).stream()
                .map(dtoMapper::toReadDto)
                .toList();
    }

    public Optional<EquipmentReadDto> get(int id) {
        return repository.findById(id)
                .map(dtoMapper::toReadDto);
    }
}
