package com.revature.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	Reservation getByUserEmail(String email);

	Reservation[] getAll();

	Reservation[] getByDay(LocalDateTime localDateTime);

	Reservation cancel(Reservation reservation);

	Reservation reschedule(Reservation reservation);
	
	
}
