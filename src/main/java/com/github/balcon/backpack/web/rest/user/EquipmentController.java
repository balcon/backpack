package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.config.SecurityConfig;
import com.github.balcon.backpack.dto.EquipmentCreateDto;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.dto.EquipmentUpdateDto;
import com.github.balcon.backpack.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.balcon.backpack.config.ApplicationConfig.API_URL;

@RestController
@RequestMapping(EquipmentController.BASE_URL)
@RequiredArgsConstructor
public class EquipmentController {
    public static final String BASE_URL = API_URL + "/user/equipments";

    private final EquipmentService service;

    @GetMapping
    public List<EquipmentReadDto> getAllOfAuthUser() {
        return service.getAllByUser(SecurityConfig.AuthUserId);
    }

    @GetMapping("/{id}")
    public EquipmentReadDto get(@PathVariable int id) {
        // TODO: 16.09.2023 exception if not exist
        // TODO: 16.09.2023 exception if not owner
        return service.get(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentReadDto create(@RequestBody EquipmentCreateDto equipmentCreateDto) {
        return service.create(equipmentCreateDto, SecurityConfig.AuthUserId);
    }

    @PutMapping("/{id}")
    public EquipmentReadDto update(@PathVariable int id,
                                   @RequestBody EquipmentUpdateDto equipmentUpdateDto) {
        return service.update(id, equipmentUpdateDto, SecurityConfig.AuthUserId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id, SecurityConfig.AuthUserId);
    }
}
