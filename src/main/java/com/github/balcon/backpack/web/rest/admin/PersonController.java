package com.github.balcon.backpack.web.rest.admin;

import com.github.balcon.backpack.dto.person.PersonCreateDto;
import com.github.balcon.backpack.dto.person.PersonReadDto;
import com.github.balcon.backpack.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@RestController
@RequestMapping(PersonController.BASE_URL)
@RequiredArgsConstructor
public class PersonController {
    public static final String BASE_URL = API_URL + "/admin/users";

    private final PersonService service;

    @GetMapping
    public List<PersonReadDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PersonReadDto getById(@PathVariable int id) {
        // TODO: 11.09.2023 Throw exception
        return service.getById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonReadDto create(@RequestBody PersonCreateDto person) {
        return service.create(person);
    }
}
