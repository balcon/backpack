package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.EquipmentFullReadDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentWriteDto;
import com.github.balcon.backpack.dto.mapper.EquipmentMapper;
import com.github.balcon.backpack.exception.ResourceNotFoundException;
import com.github.balcon.backpack.logging.Logged;
import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.Equipment;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.BackpackRepository;
import com.github.balcon.backpack.repository.EquipmentRepository;
import com.github.balcon.backpack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
@Logged
@Transactional(readOnly = true)
public class EquipmentService {
    protected static final String RESOURCE = "Equipment";

    private final EquipmentRepository equipmentRepository;
    private final BackpackRepository backpackRepository;
    private final UserRepository userRepository;
    private final EquipmentMapper mapper;

    public List<EquipmentReadDto> getAllByUser(int ownerId) {
        return equipmentRepository.findAllByOwnerId(ownerId).stream()
                .map(mapper::toReadDto)
                .toList();
    }

    public EquipmentFullReadDto get(int id, int ownerId) {
        return equipmentRepository.findByIdAndOwnerId(id, ownerId)
                .map(mapper::toFullReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    @Transactional
    public EquipmentReadDto create(EquipmentWriteDto equipmentWriteDto, int ownerId) {
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException(UserService.RESOURCE, ownerId));
        Equipment equipment = mapper.toEntity(equipmentWriteDto);
        equipment.setOwner(user);
        return mapper.toReadDto(equipmentRepository.saveAndFlush(equipment));
    }

    @Transactional
    public EquipmentReadDto update(int id, EquipmentWriteDto equipmentWriteDto, int ownerId) {
        return equipmentRepository.findByIdAndOwnerId(id, ownerId)
                .map(equipment -> mapper.toEntity(equipmentWriteDto, equipment))
                .map(equipmentRepository::saveAndFlush)
                .map(mapper::toReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    @Transactional
    public void delete(int id, int ownerId) {
        equipmentRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
        equipmentRepository.deleteById(id);
    }

    @Transactional
    public EquipmentFullReadDto addBackpack(int equipmentId, int backpackId, int ownerId) {
        return manageBackpack(equipmentId, backpackId, ownerId, Equipment::addBackpack);
    }

    @Transactional
    public EquipmentFullReadDto removeBackpack(int equipmentId, int backpackId, int ownerId) {
        return manageBackpack(equipmentId, backpackId, ownerId, Equipment::removeBackpack);
    }

    private EquipmentFullReadDto manageBackpack(int equipmentId, int backpackId, int ownerId,
                                                BiFunction<Equipment, Backpack, Equipment> action) {
        Backpack backpack = backpackRepository.findByIdAndOwnerId(backpackId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException(BackpackService.RESOURCE, backpackId));
        return equipmentRepository.findByIdAndOwnerId(equipmentId, ownerId)
                .map(equipment -> action.apply(equipment, backpack))
                .map(mapper::toFullReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, backpackId));
    }
}
