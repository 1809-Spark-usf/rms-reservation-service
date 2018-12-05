package com.revature.tests.services;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.aspectj.weaver.ast.Not;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.exceptions.BadRequestException;
import com.revature.exceptions.NotFoundException;
import com.revature.models.Reservation;
import com.revature.purposeEnum.Purpose;
import com.revature.repositories.ReservationRepository;
import com.revature.services.ReservationService;

public class ReservationTests {
	
	ReservationRepository mockReservationRepository = mock(ReservationRepository.class);
	ReservationService reservationService = new ReservationService(mockReservationRepository);
	Purpose purpose;
	LocalDateTime startTime;
	LocalDateTime endTime;
	int resourceId;
	String userEmail;
	boolean cancelled;
	
	int nonExisitingResourceId;
	
//	@Test(expected=NullPointerException.class)
//	public void getReservationNoUserEmail() throws Exception {
//		Mockito.when(mockReservationRepository.getByUserEmail(null)).thenThrow(NullPointerException.class);
//		assertNull(reservationService.getReservationByUserEmail(null));
//	}
//	
//	@Test(expected=NotFoundException.class)
//	public void getReservationNoReservations() throws Exception {
//		Mockito.when(mockReservationRepository.getByUserEmail("emailThatHasNoReservations")).thenThrow(NotFoundException.class);
//		reservationService.getReservationByUserEmail("emailThatHasNoReservations");
//	}
//	
//	@Test(expected=BadRequestException.class)
//	public void getReservationsByDay() throws Exception {
//		Mockito.when(mockReservationRepository.getByDay(null)).thenThrow(BadRequestException.class);
//		reservationService.getReservationsByDay(null);
//	}
//	
//	@Test(expected=BadRequestException.class)
//	public void getReservationsByIdZero() throws Exception {
//		Mockito.when(mockReservationRepository.getOne(0)).thenThrow(BadRequestException.class);
//		reservationService.getReservationById(0);
//	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationIdsNoStartTime() throws Exception {
		Mockito.when(mockReservationRepository
				.findAllResourceIdsByStartDateTimeAfterAndEndDateTimeBefore(null, endTime))
				.thenThrow(BadRequestException.class);
		reservationService.getReservationIds(null, endTime);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationIdsNoEndTime() throws Exception {
		Mockito.when(mockReservationRepository
				.findAllResourceIdsByStartDateTimeAfterAndEndDateTimeBefore(startTime, null))
				.thenThrow(BadRequestException.class);
		reservationService.getReservationIds(startTime, null);
	}
	
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoPurpose() throws Exception {
		Reservation badReservation = new Reservation
				(0, null, startTime, endTime, resourceId, userEmail, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoStartTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, null, endTime, resourceId, userEmail, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoEndTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, null, resourceId, userEmail, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoResourceId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, 0, userEmail, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoEmail() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, resourceId, null, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void cancelReservationIdZero() throws Exception {
		Mockito.when(mockReservationRepository.cancel(0)).thenThrow(BadRequestException.class);
		reservationService.cancelReservation(0);
		
	}
	
	@Test(expected=NotFoundException.class)
	public void cancelReservationIdNotExist() throws Exception {
		Mockito.when(mockReservationRepository.cancel(nonExisitingResourceId)).thenThrow(NotFoundException.class);
		reservationService.cancelReservation(0);	
	}
	
	@Test(expected=BadRequestException.class)
	public void rescheduleReservationNoStartTime() throws Exception {
		Mockito.when(mockReservationRepository.update(null, endTime, resourceId))
		.thenThrow(BadRequestException.class);
		reservationService.reschedule(null, endTime, resourceId);
	}
	
	@Test(expected=BadRequestException.class)
	public void rescheduleReservationNoEndTime() throws Exception {
		Mockito.when(mockReservationRepository.update(startTime, null, resourceId))
		.thenThrow(BadRequestException.class);
		reservationService.reschedule(startTime, null, resourceId);
	}
	
	@Test(expected=BadRequestException.class)
	public void rescheduleReservationIdZero() throws Exception {
		Mockito.when(mockReservationRepository.update(startTime, endTime, 0))
		.thenThrow(BadRequestException.class);
		reservationService.reschedule(startTime, endTime, 0);
	}
	
	@Test(expected=NotFoundException.class)
	public void rescheduleReservationIdNotExist() throws Exception {
		Mockito.when(mockReservationRepository.update(startTime, endTime, nonExisitingResourceId))
		.thenThrow(NotFoundException.class);
		reservationService.reschedule(startTime, endTime, nonExisitingResourceId);
	}
	
//	@Test(expected=BadRequestException.class)
//	public void cancelReservationNoReservation() throws Exception {
//		Mockito.when(reservationService.cancelReservation(null)).thenThrow(BadRequestException.class);
//		assertNull(reservationService.cancelReservation(null));
//	}
//	
//	@Test(expected=BadRequestException.class)
//	public void rescheduleReservationNoReservation() throws Exception {
//		Mockito.when(reservationService.reschedule(null)).thenThrow(BadRequestException.class);
//		assertNull(reservationService.reschedule(null));
//	}
//	
}
