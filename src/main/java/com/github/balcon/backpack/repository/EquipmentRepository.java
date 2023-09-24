package com.github.balcon.backpack.repository;

import com.github.balcon.backpack.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    List<Equipment> findAllByOwnerId(int id);

    Optional<Equipment> findByIdAndOwnerId(int id, int ownerId);
}
