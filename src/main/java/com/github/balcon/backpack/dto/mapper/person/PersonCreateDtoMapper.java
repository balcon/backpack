package com.github.balcon.backpack.dto.mapper.person;

import com.github.balcon.backpack.dto.mapper.Mapper;
import com.github.balcon.backpack.dto.person.PersonCreateDto;
import com.github.balcon.backpack.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonCreateDtoMapper implements Mapper<PersonCreateDto, Person> {
    @Override
    public Person map(PersonCreateDto person) {
        return Person.builder()
                .email(person.email())
                .name(person.name()).build();
    }
}
