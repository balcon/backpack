package com.github.balcon.backpack.web.rest.user;

import com.github.balcon.backpack.config.SecurityConfig;
import com.github.balcon.backpack.dto.EquipmentReadDto;
import com.github.balcon.backpack.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EquipmentController.BASE_URL)
@RequiredArgsConstructor
public class EquipmentController {
    public static final String BASE_URL = "/user/equipments";

    private final EquipmentService service;

    @GetMapping
    public List<EquipmentReadDto> getAllOfAuthUser() {
        int authUserId = SecurityConfig.AuthUserId;
        return service.getAllByUser(authUserId);
    }

    @GetMapping("/{id}")
    public EquipmentReadDto get(@PathVariable int id) {
        // TODO: 16.09.2023 exception if not exist
        // TODO: 16.09.2023 exception if not owner
        return service.get(id).orElseThrow();
    }
}
