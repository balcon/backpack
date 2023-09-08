package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.dto.BackpackDto;
import com.github.balcon.backpack.service.BackpackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BackpackController.BASE_URL)
@RequiredArgsConstructor
public class BackpackController {
    protected static final String BASE_URL = "/user/backpacks";

    private final BackpackService service;

    @GetMapping("/{id}")
    public BackpackDto getById(@PathVariable int id) {
        return service.getById(id);
    }
}
