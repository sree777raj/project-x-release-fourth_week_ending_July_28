package com.prakat.projectx.repo;


import com.prakat.projectx.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

   public Optional<User> findById(int id);
   public List<User> findAll();

    boolean existsByEmail(String email);
}
