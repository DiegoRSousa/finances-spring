package com.diego.finances.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.finances.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
