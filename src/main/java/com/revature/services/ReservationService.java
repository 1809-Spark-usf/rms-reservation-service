package com.revature.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.revature.models.Reservation;
import com.revature.repositories.ReservationRepository;

@Service
public class ReservationService {

	ReservationRepository reservationRepository;
	
	static LocalDateTime timeNow = LocalDateTime.now();
	
	@Autowired
	public ReservationService(ReservationRepository reservationRepository) {
		super();
		this.reservationRepository = reservationRepository;
	}
	
	public List<Reservation> getReservationsByUserId(String id) {
		return reservationRepository.findByUserId(id);	
	}
	
	public List<Reservation> getUpcomingReservationsByUserId(String id) {
		return reservationRepository.findAllByUserIdAndUpcoming(id, LocalDateTime.now());
	}
	
	public List<Reservation> getPastReservationsByUserId(String id) {
		return reservationRepository.findAllByUserIdAndPast(id, LocalDateTime.now()); 
	}
	
	public Reservation getReservationById(int id) {
		return reservationRepository.findById(id);
	}
	
	public List<Integer> getReservationResourceIds(LocalDateTime startDateTime, 
			LocalDateTime endDateTime) {
		return reservationRepository
				.findResourceIdsByStartTimeAfterAndEndTimeBefore
				(startDateTime, endDateTime);
	}
	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);		
	}

	public Reservation cancelReservation(int id) {
		return reservationRepository.cancel(id);	
	}

	
	public List<Reservation> getReservationByCriteria(Reservation reservation) {
		return reservationRepository.findAll(Example.of(reservation));
	}

	public List<Reservation> getAll() {
		return reservationRepository.findAll();
	}


}
