package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.config.AuthenticatedUser;
import com.github.balcon.backpack.dto.BackpackFullReadDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.service.BackpackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@RestController
@RequestMapping(BackpackController.BASE_URL)
@RequiredArgsConstructor
public class BackpackController {
    protected static final String BASE_URL = API_URL + "/user/backpacks";
    protected static final String COLLECTION = "/equipment";

    private final BackpackService service;

    @GetMapping
    public List<BackpackReadDto> getAllOfAuthUser(@AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.getAllByUser(authUser.getId());
    }

    @GetMapping("/{id}")
    public BackpackFullReadDto get(@PathVariable int id,
                                   @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.get(id, authUser.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BackpackReadDto create(@Valid @RequestBody BackpackWriteDto backpackWriteDto,
                                  @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.create(backpackWriteDto, authUser.getId());
    }

    @PutMapping("/{id}")
    public BackpackReadDto update(@PathVariable int id,
                                  @Valid @RequestBody BackpackWriteDto backpackWriteDto,
                                  @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.update(id, backpackWriteDto, authUser.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id,
                       @AuthenticationPrincipal AuthenticatedUser authUser) {
        service.delete(id, authUser.getId());
    }

    @PostMapping("/{backpackId}" + COLLECTION + "/{equipmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BackpackFullReadDto addEquipment(@PathVariable int backpackId,
                                            @PathVariable int equipmentId,
                                            @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.addEquipment(backpackId, equipmentId, authUser.getId());
    }

    @DeleteMapping("/{backpackId}" + COLLECTION + "/{equipmentId}")
    public BackpackFullReadDto removeEquipment(@PathVariable int backpackId,
                                               @PathVariable int equipmentId,
                                               @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.removeEquipment(backpackId, equipmentId, authUser.getId());
    }
}
