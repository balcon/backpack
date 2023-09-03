package com.github.balcon.backpack.repository;

import com.github.balcon.backpack.model.Backpack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackpackRepository extends CrudRepository<Backpack, Integer> {
}
