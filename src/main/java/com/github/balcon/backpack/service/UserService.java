package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.dto.mapper.UserMapper;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository repository;
    private final UserMapper dtoMapper;

    public List<UserReadDto> getAll() {
        return repository.findAll().stream()
                .map(dtoMapper::toReadDto)
                .toList();
    }

    public Optional<UserReadDto> get(int id) {
        return repository.findById(id)
                .map(dtoMapper::toReadDto);
    }

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto) {
        User newUser = dtoMapper.toEntity(userCreateDto);
        return dtoMapper.toReadDto(repository.saveAndFlush(newUser));
    }

    @Transactional
    public UserReadDto update(int id, UserUpdateDto userUpdateDto) {
        return repository.findById(id)
                .map(user -> dtoMapper.toEntity(userUpdateDto, user))
                .map(repository::saveAndFlush)
                .map(dtoMapper::toReadDto)
                .orElseThrow();
        // TODO: 13.09.2023 Handle exception
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }
}
