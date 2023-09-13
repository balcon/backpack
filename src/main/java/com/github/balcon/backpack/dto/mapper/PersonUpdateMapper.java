package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.PersonUpdateDto;
import com.github.balcon.backpack.model.Person;

public class PersonUpdateMapper implements Mapper<PersonUpdateDto, Person> {
    @Override
    public Person map(PersonUpdateDto entity) {
        throw new UnsupportedOperationException();
    }

    // TODO: 13.09.2023 Do it better
    public Person map(PersonUpdateDto personDto, Person person) {
        person.setName(personDto.name());
        person.setRole(personDto.role());
        return person;
    }
}
