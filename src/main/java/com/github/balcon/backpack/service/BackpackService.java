package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.dto.mapper.BackpackMapper;
import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.BackpackRepository;
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
    private final UserRepository userRepository;
    private final BackpackMapper dtoMapper;

    // TODO: 17.09.2023 check if not owner
    public Optional<BackpackReadDto> get(int id, int userId) {
        return backpackRepository.findById(id)
                .map(dtoMapper::toReadDto);
    }

    public List<BackpackReadDto> getAllByUser(int userId) {
        return backpackRepository.findAllByOwnerId(userId).stream()
                .map(dtoMapper::toReadDto)
                .toList();
    }

    @Transactional
    public BackpackReadDto create(BackpackWriteDto backpackWriteDto, int authUserId) {
        // TODO: 18.09.2023 Throw exception
        User user = userRepository.findById(authUserId).orElseThrow();
        // TODO: 18.09.2023 Change mapper
        Backpack backpack = dtoMapper.toEntity(backpackWriteDto);
        backpack.setOwner(user);

        return dtoMapper.toReadDto(backpackRepository.saveAndFlush(backpack));
    }

    public BackpackReadDto update(int id, BackpackWriteDto backpackWriteDto) {
        // TODO: 18.09.2023 Not found exception
        return backpackRepository.findById(id)
                .map(backpack -> dtoMapper.toEntity(backpackWriteDto, backpack))
                .map(backpackRepository::saveAndFlush)
                .map(dtoMapper::toReadDto)
                .orElseThrow();
    }

    public void delete(int id, int authUserId) {
        // TODO: 18.09.2023 check owner
        backpackRepository.deleteById(id);
    }
}
