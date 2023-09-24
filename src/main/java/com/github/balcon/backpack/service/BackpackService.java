package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackFullReadDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.dto.mapper.BackpackMapper;
import com.github.balcon.backpack.exceprion.ResourceNotFoundException;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BackpackService {
    private static final String RESOURCE = "Backpack";

    private final BackpackRepository backpackRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final BackpackMapper mapper;

    public BackpackFullReadDto get(int id, int ownerId) {
        return backpackRepository.findByIdAndOwnerId(id, ownerId)
                .map(mapper::toFullReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    public List<BackpackReadDto> getAllByUser(int userId) {
        return backpackRepository.findAllByOwnerId(userId).stream()
                .map(mapper::toReadDto)
                .toList();
    }

    @Transactional
    public BackpackReadDto create(BackpackWriteDto backpackWriteDto, int ownerId) {
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", ownerId));
        // TODO: 18.09.2023 Change mapper
        Backpack backpack = mapper.toEntity(backpackWriteDto);
        backpack.setOwner(user);

        return mapper.toReadDto(backpackRepository.saveAndFlush(backpack));
    }

    @Transactional
    public BackpackReadDto update(int id, BackpackWriteDto backpackWriteDto, int ownerId) {
        return backpackRepository.findByIdAndOwnerId(id, ownerId)
                .map(backpack -> mapper.toEntity(backpackWriteDto, backpack))
                .map(backpackRepository::saveAndFlush)
                .map(mapper::toReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    @Transactional
    public void delete(int id, int ownerId) {
        backpackRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
        backpackRepository.deleteById(id);
    }

    @Transactional
    public BackpackFullReadDto addEquipment(int backpackId, int equipmentId, int ownerId) {
        // TODO: 9/24/2023 Use equip service, not repo
        Equipment equipment = equipmentRepository.findByIdAndOwnerId(equipmentId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment", equipmentId));
        return backpackRepository.findByIdAndOwnerId(backpackId, ownerId)
                .map(backpack -> backpack.addEquipment(equipment))
                .map(mapper::toFullReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, backpackId));
    }

    @Transactional
    public BackpackFullReadDto removeEquipment(int backpackId, int equipmentId, int ownerId) {
        // TODO: 9/24/2023 Use equip service, not repo
        Equipment equipment = equipmentRepository.findByIdAndOwnerId(equipmentId, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment", equipmentId));
        return backpackRepository.findByIdAndOwnerId(backpackId, ownerId)
                .map(backpack -> backpack.removeEquipment(equipment))
                .map(mapper::toFullReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, backpackId));
    }
}
