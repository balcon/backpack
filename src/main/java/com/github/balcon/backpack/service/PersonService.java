package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.PersonReadDto;
import com.github.balcon.backpack.dto.mapper.PersonReadDtoMapper;
import com.github.balcon.backpack.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository repository;
    private final PersonReadDtoMapper mapper;

    public List<PersonReadDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public Optional<PersonReadDto> getById(int id) {
        return repository.findById(id)
                .map(mapper::map);
    }
}
