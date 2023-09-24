package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.dto.BackpackFullReadDto;
import com.github.balcon.backpack.dto.BackpackReadDto;
import com.github.balcon.backpack.dto.BackpackWriteDto;
import com.github.balcon.backpack.service.BackpackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;
import static com.github.balcon.backpack.config.SecurityConfig.authUserId;

@RestController
@RequestMapping(BackpackController.BASE_URL)
@RequiredArgsConstructor
public class BackpackController {
    protected static final String BASE_URL = API_URL + "/user/backpacks";
    protected static final String COLLECTION = "/equipment";

    private final BackpackService service;

    @GetMapping
    public List<BackpackReadDto> getAllOfAuthUser() {
        return service.getAllByUser(authUserId);
    }

    @GetMapping("/{id}")
    public BackpackFullReadDto get(@PathVariable int id) {
        return service.get(id, authUserId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BackpackReadDto create(@RequestBody BackpackWriteDto backpackWriteDto) {
        return service.create(backpackWriteDto, authUserId);
    }

    @PutMapping("/{id}")
    public BackpackReadDto update(@PathVariable int id,
                                  @RequestBody BackpackWriteDto backpackWriteDto) {
        return service.update(id, backpackWriteDto, authUserId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id, authUserId);
    }

    @PostMapping("/{backpackId}" + COLLECTION + "/{equipmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BackpackFullReadDto addEquipment(@PathVariable int backpackId,
                                            @PathVariable int equipmentId) {
        return service.addEquipment(backpackId, equipmentId, authUserId);
    }

    @DeleteMapping("/{backpackId}" + COLLECTION + "/{equipmentId}")
    public BackpackFullReadDto removeEquipment(@PathVariable int backpackId,
                                               @PathVariable int equipmentId) {
        return service.removeEquipment(backpackId, equipmentId, authUserId);
    }
}
