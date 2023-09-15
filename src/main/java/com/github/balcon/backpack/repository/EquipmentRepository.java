package com.github.balcon.backpack.repository;

import com.github.balcon.backpack.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    List<Equipment> findAllByOwnerId(int id);
}
