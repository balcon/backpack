package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.config.AuthenticatedUser;
import com.github.balcon.backpack.dto.UserCreateDto;
import com.github.balcon.backpack.dto.UserReadDto;
import com.github.balcon.backpack.dto.UserUpdateProfileDto;
import com.github.balcon.backpack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@RestController
@RequestMapping(ProfileController.BASE_URL)
@RequiredArgsConstructor
public class ProfileController {
    public static final String BASE_URL = API_URL + "/user/profile";

    private final UserService service;

    @GetMapping
    public UserReadDto get(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return service.get(authenticatedUser.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@Valid @RequestBody UserCreateDto userCreateDto) {
        return service.create(userCreateDto);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable int id,
                              @Valid @RequestBody UserUpdateProfileDto userUpdateProfileDto) {
        return service.update(id, userUpdateProfileDto);
    }
}
