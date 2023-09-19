package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentWriteDto;
import com.github.balcon.backpack.dto.mapper.EquipmentMapper;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.EquipmentRepository;
import com.github.balcon.backpack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final EquipmentMapper dtoMapper;

    public List<EquipmentReadDto> getAllByUser(int authUserId) {
        return equipmentRepository.findAllByOwnerId(authUserId).stream()
                .map(dtoMapper::toReadDto)
                .toList();
    }

    // TODO: 17.09.2023 check if not owner
    public Optional<EquipmentReadDto> get(int id) {
        return equipmentRepository.findById(id)
                .map(dtoMapper::toReadDto);
    }

    @Transactional
    public EquipmentReadDto create(EquipmentWriteDto equipmentWriteDto, int authUserId) {
        // TODO: 16.09.2023 throw exception if not exists
        User user = userRepository.findById(authUserId).orElseThrow();
        // TODO: 16.09.2023 change mapper
        Equipment equipment = dtoMapper.toEntity(equipmentWriteDto);
        equipment.setOwner(user);
        return dtoMapper.toReadDto(equipmentRepository.saveAndFlush(equipment));
    }

    @Transactional
    public EquipmentReadDto update(int id, EquipmentWriteDto equipmentWriteDto, int authUserId) {
        // TODO: 16.09.2023 check if not owner
        return equipmentRepository.findById(id)
                .map(equipment -> dtoMapper.toEntity(equipmentWriteDto, equipment))
                .map(equipmentRepository::saveAndFlush)
                .map(dtoMapper::toReadDto)
                .orElseThrow();
        // TODO: 16.09.2023 Exception???
    }

    @Transactional
    public void delete(int id, int authUserId) {
        // TODO: 16.09.2023 check if not owner
        equipmentRepository.deleteById(id);
        // TODO: 16.09.2023 Exception if not found
    }
}
