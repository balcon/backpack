package com.github.balcon.backpack.service;

import com.github.balcon.backpack.config.AuthenticatedUser;
import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.dto.UserUpdateProfileDto;
import com.github.balcon.backpack.dto.mapper.UserMapper;
import com.github.balcon.backpack.exception.ResourceNotFoundException;
import com.github.balcon.backpack.model.User;
import com.github.balcon.backpack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    protected static final String RESOURCE = "User";

    private final UserRepository repository;
    private final UserMapper dtoMapper;
    private final PasswordEncoder encoder;

    public List<UserReadDto> getAll() {
        return repository.findAll().stream()
                .map(dtoMapper::toReadDto)
                .toList();
    }

    public UserReadDto get(int id) {
        return repository.findById(id)
                .map(dtoMapper::toReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto) {
        User user = dtoMapper.toEntity(userCreateDto);
        user.setPassword(encoder.encode(user.getPassword()));
        return dtoMapper.toReadDto(repository.saveAndFlush(user));
    }

    @Transactional
    public UserReadDto update(int id, UserUpdateDto userUpdateDto) {
        return repository.findById(id)
                .map(user -> dtoMapper.toEntity(userUpdateDto, user))
                .map(repository::saveAndFlush)
                .map(dtoMapper::toReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    // TODO: 15.10.2023 Refactor duplicate code
    @Transactional
    public UserReadDto update(int id, UserUpdateProfileDto userUpdateProfileDto) {
        return repository.findById(id)
                .map(user -> dtoMapper.toEntity(userUpdateProfileDto, user))
                .map(repository::saveAndFlush)
                .map(dtoMapper::toReadDto)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    @Transactional
    public void delete(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(AuthenticatedUser::of)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User [%s] not found".formatted(username)));
    }
}
