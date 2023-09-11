package com.github.balcon.backpack.repository;

import com.github.balcon.backpack.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
