package com.github.balcon.backpack.dto.mapper.person;

import com.github.balcon.backpack.dto.mapper.Mapper;
import com.github.balcon.backpack.dto.person.PersonReadDto;
import com.github.balcon.backpack.model.Person;
import org.springframework.stereotype.Component;

@Component
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
