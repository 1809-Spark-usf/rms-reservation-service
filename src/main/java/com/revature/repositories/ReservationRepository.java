package com.revature.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.revature.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>, QueryByExampleExecutor<Reservation> {
	
//	public List<Reservation> findAll();
	
//	Reservation getByUserEmail(String email);
	
//	Reservation[] getAll();
	
//	List<Reservation> findAll();
//
//	Reservation[] getByDay(LocalDateTime localDateTime);
	
//	Reservation(;
	
//	Reservation[] findByUserNameOrPurposeOrStartTimeAndEndTimeOrResourceIdOrCancelledOrApproved();

	Reservation cancel(Reservation reservation);

	Reservation update(Reservation reservation);
	
	
}
