package com.github.balcon.backpack.service;

import com.github.balcon.backpack.dto.PersonCreateDto;
import com.github.balcon.backpack.dto.PersonReadDto;
import com.github.balcon.backpack.dto.PersonUpdateDto;
import com.github.balcon.backpack.dto.mapper.PersonCreateMapper;
import com.github.balcon.backpack.dto.mapper.PersonReadMapper;
import com.github.balcon.backpack.dto.mapper.PersonUpdateMapper;
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
    private final PersonReadMapper readMapper;
    private final PersonCreateMapper createMapper;
    private final PersonUpdateMapper updateMapper;

    public List<PersonReadDto> getAll() {
        return repository.findAll().stream()
                .map(readMapper::map)
                .toList();
    }

    public Optional<PersonReadDto> get(int id) {
        return repository.findById(id)
                .map(readMapper::map);
    }

    @Transactional
    public PersonReadDto create(PersonCreateDto person) {
        Person newPerson = createMapper.map(person);
        return readMapper.map(repository.saveAndFlush(newPerson));
    }

    @Transactional
    public PersonReadDto update(int id, PersonUpdateDto personDto) {
        return repository.findById(id)
                .map(user -> updateMapper.map(personDto, user))
                .map(repository::saveAndFlush)
                .map(readMapper::map)
                .orElseThrow();
        // TODO: 13.09.2023 Handle exception
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }
}
