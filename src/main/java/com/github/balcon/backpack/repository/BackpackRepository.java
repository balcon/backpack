package com.github.balcon.backpack.repository;

import com.github.balcon.backpack.model.Backpack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackpackRepository extends JpaRepository<Backpack, Integer> {
}
