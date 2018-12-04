package com.revature.tests.services;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.Test;
import org.mockito.Mockito;

import com.revature.exceptions.BadRequestException;
import com.revature.exceptions.NotFoundException;
import com.revature.models.Reservation;
import com.revature.purposeEnum.Purpose;
import com.revature.repositories.ReservationRepository;
import com.revature.services.ReservationService;

public class ReservationTests {
	
	ReservationRepository mockReservationRepository;
	ReservationService reservationService = mock(ReservationService.class);
	Purpose purpose;
	LocalDateTime startTime;
	LocalDateTime endTime;
	int resourceId;
	String userEmail;
	boolean cancelled;
	
	@Test(expected=NullPointerException.class)
	public void getReservationNoUserEmail() throws Exception {
		Mockito.when(reservationService.getReservationByUserEmail(null)).thenThrow(NullPointerException.class);
		assertNull(reservationService.getReservationByUserEmail(null));
	}
	
	@Test(expected=NotFoundException.class)
	public void getReservationNoReservations() throws Exception {
		Mockito.when(reservationService.getReservationByUserEmail("emailThatHasNoReservations")).thenThrow(NotFoundException.class);
		reservationService.getReservationByUserEmail("emailThatHasNoReservations");
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationsByDay() throws Exception {
		Mockito.when(reservationService.getReservationsByDay(null)).thenThrow(BadRequestException.class);
		reservationService.getReservationsByDay(null);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoPurpose() throws Exception {
		Reservation badReservation = new Reservation
				(0, null, startTime, endTime, resourceId, userEmail, false);
		Mockito.when(reservationService.saveReservation(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoStartTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, null, endTime, resourceId, userEmail, false);
		Mockito.when(reservationService.saveReservation(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoEndTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, null, resourceId, userEmail, false);
		Mockito.when(reservationService.saveReservation(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoResourceId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, 0, userEmail, false);
		Mockito.when(reservationService.saveReservation(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoEmail() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, resourceId, null, false);
		Mockito.when(reservationService.saveReservation(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void cancelReservationNoReservation() throws Exception {
		Mockito.when(reservationService.cancelReservation(null)).thenThrow(BadRequestException.class);
		assertNull(reservationService.cancelReservation(null));
	}
	
	@Test(expected=BadRequestException.class)
	public void rescheduleReservationNoReservation() throws Exception {
		Mockito.when(reservationService.reschedule(null)).thenThrow(BadRequestException.class);
		assertNull(reservationService.reschedule(null));
	}
	
	
	
}
