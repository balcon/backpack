package com.github.balcon.backpack.repository;

import com.github.balcon.backpack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
