package com.ecommerce.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.domain.Role;
import com.ecommerce.app.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	   Optional<User> findByUsername(String username);

	    Boolean existsByUsername(String username);

	    Boolean existsByEmail(String email);



}
