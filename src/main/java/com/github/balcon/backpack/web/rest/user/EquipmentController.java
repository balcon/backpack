package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.dto.EquipmentFullReadDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentWriteDto;
import com.github.balcon.backpack.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;
import static com.github.balcon.backpack.config.SecurityConfig.authUserId;

@RestController
@RequestMapping(EquipmentController.BASE_URL)
@RequiredArgsConstructor
public class EquipmentController {
    public static final String BASE_URL = API_URL + "/user/equipments";
    public static final String COLLECTION = "/backpacks";

    private final EquipmentService service;

    @GetMapping
    public List<EquipmentReadDto> getAllOfAuthUser() {
        return service.getAllByUser(authUserId);
    }

    @GetMapping("/{id}")
    public EquipmentFullReadDto get(@PathVariable int id) {
        return service.get(id, authUserId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentReadDto create(@RequestBody EquipmentWriteDto equipmentWriteDto) {
        return service.create(equipmentWriteDto, authUserId);
    }

    @PutMapping("/{id}")
    public EquipmentReadDto update(@PathVariable int id,
                                   @RequestBody EquipmentWriteDto equipmentWriteDto) {
        return service.update(id, equipmentWriteDto, authUserId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id, authUserId);
    }

    @PostMapping("/{equipmentId}" + COLLECTION + "/{backpackId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentFullReadDto addBackpack(@PathVariable int equipmentId,
                                            @PathVariable int backpackId) {
        return service.addBackpack(equipmentId, backpackId, authUserId);
    }

    @DeleteMapping("/{equipmentId}" + COLLECTION + "/{backpackId}")
    public EquipmentFullReadDto removeBackpack(@PathVariable int equipmentId,
                                               @PathVariable int backpackId) {
        return service.removeBackpack(equipmentId, backpackId, authUserId);
    }
}
