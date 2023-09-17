package com.github.balcon.backpack.repository;

import com.github.balcon.backpack.model.Backpack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackpackRepository extends JpaRepository<Backpack, Integer> {
    List<Backpack> findAllByOwnerId(int ownerId);
}
