package com.github.balcon.backpack.web.rest.admin;

import com.github.balcon.backpack.dto.PersonCreateDto;
import com.github.balcon.backpack.dto.PersonReadDto;
import com.github.balcon.backpack.dto.PersonUpdateDto;
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
    public PersonReadDto get(@PathVariable int id) {
        // TODO: 11.09.2023 Throw exception
        return service.get(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonReadDto create(@RequestBody PersonCreateDto person) {
        return service.create(person);
    }

    @PutMapping("/{id}")
    public PersonReadDto update(@PathVariable int id, @RequestBody PersonUpdateDto person) {
        return service.update(id, person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
