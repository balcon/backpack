package com.github.balcon.backpack.dto.mapper;

import com.github.balcon.backpack.dto.PersonCreateDto;
import com.github.balcon.backpack.model.Person;

public class PersonCreateDtoMapper implements Mapper<PersonCreateDto, Person> {
    @Override
    public Person map(PersonCreateDto person) {
        return Person.builder()
                .email(person.email())
                .name(person.name()).build();
    }
}
