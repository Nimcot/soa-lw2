package com.example.lw2.repos;

import com.example.lw2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<User, Integer> {}