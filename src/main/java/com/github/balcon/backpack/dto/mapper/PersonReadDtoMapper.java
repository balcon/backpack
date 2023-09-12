package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.PersonReadDto;
import com.github.balcon.backpack.model.Person;

public class PersonReadDtoMapper implements Mapper<Person, PersonReadDto> {
    @Override
    public PersonReadDto map(Person person) {
        return new PersonReadDto(
                person.getId(),
                person.getEmail(),
                person.getName(),
                person.getRole().name());
    }
}
