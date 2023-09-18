package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.config.SecurityConfig;
import com.github.balcon.backpack.dto.BackpackCreateDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.service.BackpackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@RestController
@RequestMapping(BackpackController.BASE_URL)
@RequiredArgsConstructor
public class BackpackController {
    protected static final String BASE_URL = API_URL + "/user/backpacks";

    private final BackpackService service;

    @GetMapping
    public List<BackpackReadDto> getAllOfAuthUser() {
        return service.getAllByUser(SecurityConfig.AuthUserId);
    }

    @GetMapping("/{id}")
    public BackpackReadDto get(@PathVariable int id) {
        return service.get(id, SecurityConfig.AuthUserId).orElseThrow();
        // TODO: 17.09.2023 throw exception
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BackpackReadDto create(@RequestBody BackpackCreateDto backpackCreateDto) {
        return service.create(backpackCreateDto, SecurityConfig.AuthUserId);
    }

    @PutMapping("/{id}")
    public BackpackReadDto update(@PathVariable int id,
                                  @RequestBody BackpackCreateDto backpackCreateDto) {
        return service.update(id, backpackCreateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id, SecurityConfig.AuthUserId);
    }
}
