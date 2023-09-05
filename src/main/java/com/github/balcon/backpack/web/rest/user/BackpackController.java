package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.model.Backpack;
import com.github.balcon.backpack.service.BackpackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BackpackController.BASE_URL)
@RequiredArgsConstructor
public class BackpackController {
    protected static final String BASE_URL = "/user/backpacks";

    private final BackpackService service;

    // TODO: 05.09.2023 return dto
    @GetMapping
    public List<Backpack> getAll() {
        return service.getAll();
    }
}
