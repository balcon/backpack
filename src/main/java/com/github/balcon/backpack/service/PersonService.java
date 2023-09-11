package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.mapper.person.PersonCreateDtoMapper;
import com.github.balcon.backpack.dto.mapper.person.PersonReadDtoMapper;
import com.github.balcon.backpack.dto.person.PersonCreateDto;
import com.github.balcon.backpack.dto.person.PersonReadDto;
import com.github.balcon.backpack.model.Person;
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
    private final PersonReadDtoMapper readDtoMapper;
    private final PersonCreateDtoMapper createDtoMapper;

    public List<PersonReadDto> getAll() {
        return repository.findAll().stream()
                .map(readDtoMapper::map)
                .toList();
    }

    public Optional<PersonReadDto> getById(int id) {
        return repository.findById(id)
                .map(readDtoMapper::map);
    }

    public PersonReadDto create(PersonCreateDto person) {
        Person newPerson = createDtoMapper.map(person);
        return readDtoMapper.map(repository.save(newPerson));
    }
}
