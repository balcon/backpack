package com.github.balcon.backpack.web.rest.admin;

import com.github.balcon.backpack.dto.PersonReadDto;
import com.github.balcon.backpack.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(PersonController.BASE_URL)
@RequiredArgsConstructor
public class PersonController {
    public static final String BASE_URL = "/admin/users";

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
}
