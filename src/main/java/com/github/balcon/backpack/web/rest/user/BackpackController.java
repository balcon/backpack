package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.config.SecurityConfig;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.service.BackpackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
