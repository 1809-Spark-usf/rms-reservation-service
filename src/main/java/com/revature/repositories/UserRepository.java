package com.revature.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByTokenAndExpirationAfter(String token, LocalDate lastLogin);

	Optional<User> findByToken(String token);

}
