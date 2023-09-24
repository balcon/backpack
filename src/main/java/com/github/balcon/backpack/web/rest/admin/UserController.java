package com.github.balcon.backpack.web.rest.admin;

import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateDto;
import com.github.balcon.backpack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@RestController
@RequestMapping(UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {
    public static final String BASE_URL = API_URL + "/admin/users";

    private final UserService service;

    @GetMapping
    public List<UserReadDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserReadDto get(@PathVariable int id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@RequestBody UserCreateDto userCreateDto) {
        return service.create(userCreateDto);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable int id,
                              @RequestBody UserUpdateDto userUpdateDto) {
        return service.update(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
