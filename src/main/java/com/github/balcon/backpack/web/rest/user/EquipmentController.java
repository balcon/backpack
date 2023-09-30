package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.config.AuthenticatedUser;
import com.github.balcon.backpack.dto.EquipmentFullReadDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentWriteDto;
import com.github.balcon.backpack.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@RestController
@RequestMapping(EquipmentController.BASE_URL)
@RequiredArgsConstructor
public class EquipmentController {
    public static final String BASE_URL = API_URL + "/user/equipments";
    public static final String COLLECTION = "/backpacks";

    private final EquipmentService service;

    @GetMapping
    public List<EquipmentReadDto> getAllOfAuthUser(@AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.getAllByUser(authUser.getId());
    }

    @GetMapping("/{id}")
    public EquipmentFullReadDto get(@PathVariable int id,
                                    @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.get(id, authUser.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentReadDto create(@Valid @RequestBody EquipmentWriteDto equipmentWriteDto,
                                   @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.create(equipmentWriteDto, authUser.getId());
    }

    @PutMapping("/{id}")
    public EquipmentReadDto update(@PathVariable int id,
                                   @Valid @RequestBody EquipmentWriteDto equipmentWriteDto,
                                   @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.update(id, equipmentWriteDto, authUser.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id,
                       @AuthenticationPrincipal AuthenticatedUser authUser) {
        service.delete(id, authUser.getId());
    }

    @PostMapping("/{equipmentId}" + COLLECTION + "/{backpackId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentFullReadDto addBackpack(@PathVariable int equipmentId,
                                            @PathVariable int backpackId,
                                            @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.addBackpack(equipmentId, backpackId, authUser.getId());
    }

    @DeleteMapping("/{equipmentId}" + COLLECTION + "/{backpackId}")
    public EquipmentFullReadDto removeBackpack(@PathVariable int equipmentId,
                                               @PathVariable int backpackId,
                                               @AuthenticationPrincipal AuthenticatedUser authUser) {
        return service.removeBackpack(equipmentId, backpackId, authUser.getId());
    }
}
