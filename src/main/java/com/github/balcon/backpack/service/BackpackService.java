package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackFullReadDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.dto.mapper.BackpackMapper;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BackpackService {
    private final BackpackRepository backpackRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final BackpackMapper mapper;

    // TODO: 17.09.2023 check if not owner
    public Optional<BackpackFullReadDto> get(int id, int authUserId) {
        return backpackRepository.findById(id)
                .map(mapper::toFullReadDto);
    }

    public List<BackpackReadDto> getAllByUser(int userId) {
        return backpackRepository.findAllByOwnerId(userId).stream()
                .map(mapper::toReadDto)
                .toList();
    }

    @Transactional
    public BackpackReadDto create(BackpackWriteDto backpackWriteDto, int authUserId) {
        // TODO: 18.09.2023 Throw exception
        User user = userRepository.findById(authUserId).orElseThrow();
        // TODO: 18.09.2023 Change mapper
        Backpack backpack = mapper.toEntity(backpackWriteDto);
        backpack.setOwner(user);

        return mapper.toReadDto(backpackRepository.saveAndFlush(backpack));
    }

    @Transactional
    public BackpackReadDto update(int id, BackpackWriteDto backpackWriteDto) {
        // TODO: 18.09.2023 Not found exception
        return backpackRepository.findById(id)
                .map(backpack -> mapper.toEntity(backpackWriteDto, backpack))
                .map(backpackRepository::saveAndFlush)
                .map(mapper::toReadDto)
                .orElseThrow();
    }

    @Transactional
    public void delete(int id, int authUserId) {
        // TODO: 18.09.2023 check owner
        backpackRepository.deleteById(id);
    }

    @Transactional
    public Optional<BackpackFullReadDto> addEquipment(int backpackId, int equipmentId, int authUserId) {
        // TODO: 19.09.2023 Exception check owner
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();
        return backpackRepository.findById(backpackId)
                .map(backpack -> backpack.addEquipment(equipment))
                .map(mapper::toFullReadDto);
    }

    @Transactional
    public Optional<BackpackFullReadDto> removeEquipment(int backpackId, int equipmentId, int authUserId) {
        // TODO: 19.09.2023 check owner
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow();
        return backpackRepository.findById(backpackId)
                .map(backpack -> backpack.removeEquipment(equipment))
                .map(mapper::toFullReadDto);
    }
}
