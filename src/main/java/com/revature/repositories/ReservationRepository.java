package com.revature.repositories;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.revature.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>, QueryByExampleExecutor<Reservation> {
	
	static LocalDateTime timeNow = LocalDateTime.now();

	@Query(value = "SELECT resource_Id FROM reservations WHERE start_time >= ?1 AND end_time <= ?2", nativeQuery = true)
	public List<Integer> findResourceIdsByStartTimeAfterAndEndTimeBefore
	(LocalDateTime startTime, LocalDateTime endTime);
	
	@Query("select r from Reservation r where r.startTime > :todayNow and userId = :id")
	public List<Reservation> findAllByUserIdAndUpcoming(@Param("id") String id, 
			@Param("todayNow") LocalDateTime timeNow);
	
	@Query("select r from Reservation r where r.startTime < :todayNow and userId = :id")
	public List<Reservation> findAllByUserIdAndPast(@Param("id") String id, 
			@Param("todayNow") LocalDateTime timeNow);
	
	public List<Reservation> findByUserId(String id);
	
	public Reservation findById(int id);
	
	@Modifying
	@Query("update Reservation r set r.cancelled = true where r.id = :id")
	public Reservation cancel(@Param("id") int id);
//	
//	public Reservation update();

	
//	public List<Reservation> findAll();
	
//	Reservation getByUserEmail(String email);
	
//	Reservation[] getAll();
	
//	List<Reservation> findAll();
//
//	Reservation[] getByDay(LocalDateTime localDateTime);
	
//	Reservation(;
	
//	Reservation[] findByUserNameOrPurposeOrStartTimeAndEndTimeOrResourceIdOrCancelledOrApproved();
	
	
	
	
	
//	@Modifying
//	@Query("update Reservation r set r.startTime = :startTime AND r.endTime = :endTime where r.id = :id")
//	public Reservation update(@Param("startTime") LocalDateTime startDateTime, 
//			@Param("endTime") LocalDateTime endDateTime, @Param("id") int id);
	
	
}
