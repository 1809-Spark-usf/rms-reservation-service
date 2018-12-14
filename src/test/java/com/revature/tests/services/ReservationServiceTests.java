package com.revature.tests.services;

import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.enumerations.Purpose;
import com.revature.exceptions.BadRequestException;
import com.revature.exceptions.NotFoundException;
import com.revature.models.Reservation;
import com.revature.repositories.ReservationRepository;
import com.revature.services.ReservationService;

public class ReservationServiceTests {
	
	ReservationRepository mockReservationRepository = mock(ReservationRepository.class);
	ReservationService reservationService = new ReservationService(mockReservationRepository);
	Purpose purpose;
	LocalDateTime startTime;
	LocalDateTime endTime;
	int resourceId;
	String userId;
	boolean cancelled;
	int nonExisitingResourceId;
	String nonExistingUserId;
	int nonExistingReservationId;
	LocalDateTime timeNow = LocalDateTime.now();
	boolean upcoming;
	
	@Test(expected=BadRequestException.class)
	public void getReservationsByUserIdNull() throws Exception {
		Mockito.when(mockReservationRepository.findByUserId(null))
		.thenThrow(BadRequestException.class);
		reservationService.getReservationsByUserId(null);
	}
	
	@Test(expected=NotFoundException.class)
	public void getReservationsByUserIdNotFound() throws Exception {
		Mockito.when(mockReservationRepository.findByUserId(nonExistingUserId))
		.thenThrow(NotFoundException.class);
		reservationService.getReservationsByUserId(nonExistingUserId);
	}
	
	@Ignore
	@Test(expected=NotFoundException.class)
	public void getReservationsUpcomingByUserIdNotFound() throws Exception {
		Mockito.when(mockReservationRepository.findAllByUserIdAndUpcoming
				(nonExistingUserId, LocalDateTime.now())).thenThrow(NotFoundException.class);
		reservationService.getUpcomingReservationsByUserId(nonExistingUserId);
	}
	
	@Ignore
	@Test(expected=NotFoundException.class)
	public void getReservationsPastByUserIdNotFound() throws Exception {
		Mockito.when(mockReservationRepository.findAllByUserIdAndPast
				(nonExistingUserId, LocalDateTime.now())).thenThrow(NotFoundException.class);
		reservationService.getPastReservationsByUserId(nonExistingUserId);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationResourceIdsNoStartTime() throws Exception {
		Mockito.when(mockReservationRepository
				.findResourceIdsByStartTimeAfterAndEndTimeBefore
				(null, endTime)).thenThrow(BadRequestException.class);
		reservationService.getReservationResourceIds(null, endTime);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationResourceIdsNoEndTime() throws Exception {
		Mockito.when(mockReservationRepository
				.findResourceIdsByStartTimeAfterAndEndTimeBefore
				(startTime, null)).thenThrow(BadRequestException.class);
		reservationService.getReservationResourceIds(startTime, null);
	}	
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoPurpose() throws Exception {
		Reservation badReservation = new Reservation
				(0, null, startTime, endTime, null, resourceId, userId, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoStartTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, null, endTime, null, resourceId, userId, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoEndTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, null, null, resourceId, userId, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationZeroResourceId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, 0, userId, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNotFoundResourceId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, nonExisitingResourceId, userId, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoUserId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, resourceId, null, false, true);
		Mockito.when(mockReservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNotFoundUserId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, resourceId, nonExistingUserId, false, true);
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
		
}
