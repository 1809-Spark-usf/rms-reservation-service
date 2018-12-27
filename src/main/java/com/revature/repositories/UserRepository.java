package com.revature.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

/**
 * The Interface UserRepository.
 * @author 1811-Java-Nick 12/27/18
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	/**
	 * Return a user by token and last login.
	 *
	 * @param token the token
	 * @param lastLogin the last login
	 * @return user object
	 */
	Optional<User> findByTokenAndExpirationAfter(String token, LocalDate lastLogin);

	/**
	 * Return a user by token.
	 *
	 * @param token the token
	 * @return user object
	 */
	Optional<User> findByToken(String token);

}
