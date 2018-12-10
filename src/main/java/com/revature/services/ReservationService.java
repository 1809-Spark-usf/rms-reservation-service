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
	
	public List<Reservation> getReservationsByUserId(int id) {
		return reservationRepository.findByUserId(id);	
	};
	
	public List<Reservation> getUpcomingReservationsByUserId(int id) {
		return reservationRepository.findAllByUserIdAndUpcoming(id, timeNow);
	}
	
	public List<Reservation> getPastReservationsByUserId(int id) {
		return reservationRepository.findAllByUserIdAndPast(id, timeNow); 
	}
	
	public Reservation getReservationById(int id) {
		return reservationRepository.findById(id);
	}
	
	public int[] getReservationResourceIds(LocalDateTime startDateTime, 
			LocalDateTime endDateTime) {
		return reservationRepository
				.findAllResourceIdsByStartTimeAfterAndEndTimeBefore
				(startDateTime, endDateTime);
	}
	public Reservation saveReservation(Reservation reservation) {
//		if (reservation.getPurpose() == null || reservation.getStartTime() == null
//				|| reservation.getEndTime() == null || reservation.getResourceID() != 0
//				|| reservation.getUserEmail() == null) {
//			throw new BadRequestException();
//		}
		return reservationRepository.save(reservation);		
	};

	public Reservation cancelReservation(int id) {
		return reservationRepository.cancel(id);	
	};

	
	public List<Reservation> getReservationByCriteria(Reservation reservation) {
		return reservationRepository.findAll(Example.of(reservation));
	}

	public List<Reservation> getAll() {
		// TODO Auto-generated method stub
		return reservationRepository.findAll();
	}

	
	
	

	

//	public Reservation reschedule(LocalDateTime startTime, LocalDateTime endTime, int id) {
//		return reservationRepository.update(startTime, endTime, id);	
//	};

//	public Reservation getReservationById(int id) {
//		return reservationRepository.getOne(id);
//	}
//
//	public Reservation[] getAllReservations() {
//		return reservationRepository.getAll();
//	};
//
//	public Reservation[] getReservationsByDay(LocalDateTime localDateTime) {
//		return reservationRepository.getByDay(localDateTime);
//	};
	
//	public Reservation[] getReservationsByQuery(String userEmail, Purpose purpose, 
//			LocalDateTime startTime, LocalDateTime endTime, int resourceId, boolean cancelled, boolean approved) {
//		return reservationRepository.findByUserNameOrPurposeOrStartTimeAndEndTimeOrResourceIdOrCancelledOrApproved();
//		
//	}


}
