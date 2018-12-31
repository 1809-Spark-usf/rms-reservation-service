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

/**
 * The Interface ReservationRepository.
 * @author 1811-Java-Nick 12/27/18
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>, QueryByExampleExecutor<Reservation> {
	
	/** The time now. */
	static LocalDateTime timeNow = LocalDateTime.now();

	/**
	 * Find resource ids between a start time and an end time.
	 *
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the list
	 */
	@Query(value = "SELECT resource_Id FROM reservations WHERE start_time >= ?1 AND end_time <= ?2", nativeQuery = true)
	public List<Integer> findResourceIdsByStartTimeAfterAndEndTimeBefore
	(LocalDateTime startTime, LocalDateTime endTime);
	
	/**
	 * Find all reservations by user id and after the given start time.
	 *
	 * @param id the id
	 * @param timeNow the time now
	 * @return the list
	 */
	@Query("select r from Reservation r where r.startTime > :todayNow and userId = :id")
	public List<Reservation> findAllByUserIdAndUpcoming(@Param("id") String id, 
			@Param("todayNow") LocalDateTime timeNow);
	
	/**
	 * Find all reservations by user id and before the given start time.
	 *
	 * @param id the id
	 * @param timeNow the time now
	 * @return the list
	 */
	@Query("select r from Reservation r where r.startTime < :todayNow and userId = :id")
	public List<Reservation> findAllByUserIdAndPast(@Param("id") String id, 
			@Param("todayNow") LocalDateTime timeNow);
	
	/**
	 * Find all reservations by user id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<Reservation> findByUserId(String id);
	
	/**
	 * Find a reservation by user id.
	 *
	 * @param id the id
	 * @return the reservation
	 */
	public Reservation findById(int id);
	
	/**
	 * Cancel a reservation by user id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Query(value = "UPDATE reservations SET cancelled = true WHERE id = ?1 RETURNING id", nativeQuery = true)
	public int cancel(int id);
	
	/**
	 * List all reservations.
	 *
	 * @return list of reservations
	 */
	public List<Reservation> findAll();

}
