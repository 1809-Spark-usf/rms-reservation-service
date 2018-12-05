package com.revature.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.revature.models.Reservation;
import com.revature.purposeEnum.Purpose;
import com.revature.repositories.ReservationRepository;

@Service
public class ReservationService {

	ReservationRepository reservationRepository;

	@Autowired
	public ReservationService(ReservationRepository reservationRepository) {
		super();
		this.reservationRepository = reservationRepository;
	}
	
	
//	Example<Reservation> reservationExample = Example.of(reservation);
	
//	public List<Reservation> getReservationByCriteria(int id, Purpose purpose, 
//			String userEmail, boolean cancelled, boolean approved) {
//		Reservation reservation = new Reservation();
//		reservation.setId(id);
//		reservation.setPurpose(purpose);
//		reservation.setUserEmail(userEmail);
//		reservation.setCancelled(cancelled);
//		reservation.setApproved(approved);
//		return reservationRepository.findAll(Example.of(reservation));
//	}
	
	public List<Reservation> getReservationIds(LocalDateTime startDateTime, 
			LocalDateTime endDateTime) {
		return reservationRepository
				.findAllResourceIdByStartDateTimeAfterAndEndDateTimeBefore
				(startDateTime, endDateTime);
	}

//	public Reservation getReservationByUserEmail(String email) {
//		return reservationRepository.getByUserEmail(email);
//	};
//	
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

	public Reservation reschedule(LocalDateTime startTime, LocalDateTime endTime, int id) {
		return reservationRepository.update(endTime, endTime, id);	
	};
}
