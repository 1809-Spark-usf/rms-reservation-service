package com.revature.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Reservation;
import com.revature.repositories.ReservationRepository;

@Service
public class ReservationService {

	ReservationRepository reservationRepository;

	@Autowired
	public ReservationService(ReservationRepository reservationRepository) {
		super();
		this.reservationRepository = reservationRepository;
	}

	public Reservation getReservationByUserEmail(String email) {
		return reservationRepository.getByUserEmail(email);
	};
	
	public Reservation getReservationById(int id) {
		return reservationRepository.getOne(id);
	}

	public Reservation[] getAllReservations() {
		return reservationRepository.getAll();
	};

	public Reservation[] getReservationsByDay(LocalDateTime localDateTime) {
		return reservationRepository.getByDay(localDateTime);
	};

	public Reservation saveReservation(Reservation reservation) {
//		if (reservation.getPurpose() == null || reservation.getStartTime() == null
//				|| reservation.getEndTime() == null || reservation.getResourceID() != 0
//				|| reservation.getUserEmail() == null) {
//			throw new BadRequestException();
//		}
		return reservationRepository.save(reservation);		
	};

	public Reservation cancelReservation(Reservation reservation) {
		return reservationRepository.cancel(reservation);	
	};

	public Reservation reschedule(Reservation reservation) {
		return reservationRepository.reschedule(reservation);	
	};
}
