package com.revature.tests.services;

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

public class ReservationServiceTests {
	
	ReservationRepository mockReservationRepository = mock(ReservationRepository.class);
	ReservationService reservationService = new ReservationService(mockReservationRepository);
	Purpose purpose;
	LocalDateTime startTime;
	LocalDateTime endTime;
	int resourceId;
	int userId;
	boolean cancelled;
	int nonExisitingResourceId;
	int nonExistingUserId;
	int nonExistingReservationId;
	LocalDateTime timeNow = LocalDateTime.now();
	boolean upcoming;
	
	@Test(expected=BadRequestException.class)
	public void getReservationsByUserIdZero() throws Exception {
		Mockito.when(mockReservationRepository.findAllByUserId(0))
		.thenThrow(BadRequestException.class);
		reservationService.getReservationsByUserId(0);
	}
	
	@Test(expected=NotFoundException.class)
	public void getReservationsByUserIdNotFound() throws Exception {
		Mockito.when(mockReservationRepository.findAllByUserId(nonExistingUserId))
		.thenThrow(NotFoundException.class);
		reservationService.getReservationsByUserId(0);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationsUpcomingByUserIdZero() throws Exception {
		Mockito.when(mockReservationRepository.findAllByUserIdAndUpcoming
				(0, timeNow)).thenThrow(BadRequestException.class);
		reservationService.getUpcomingReservationsByUserId(0);
	}
	
	@Test(expected=NotFoundException.class)
	public void getReservationsUpcomingByUserIdNotFound() throws Exception {
//		Mockito.when(mockReservationRepository.findAllByUserIdAndUpcoming
//				(nonExistingUserId, timeNow)).thenThrow(NotFoundException.class);
//		reservationService.getUpcomingReservationsByUserId(nonExistingUserId);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationsPastByUserIdZero() throws Exception {
//		Mockito.when(mockReservationRepository.findAllByUserIdAndPast
//				(0, timeNow)).thenThrow(BadRequestException.class);
//		reservationService.getPastReservationsByUserId(0);
	}
	
	@Test(expected=NotFoundException.class)
	public void getReservationsPastByUserIdNotFound() throws Exception {
//		Mockito.when(mockReservationRepository.findAllByUserIdAndPast
//				(nonExistingUserId, timeNow)).thenThrow(NotFoundException.class);
//		reservationService.getPastReservationsByUserId(nonExistingUserId);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationZeroId() throws Exception {
		Mockito.when(mockReservationRepository.getOne(0))
		.thenThrow(BadRequestException.class);
		reservationService.getReservationById(0);
	}
	
	@Test(expected=NotFoundException.class)
	public void getReservationIdNotFound() throws Exception {
		Mockito.when(mockReservationRepository.getOne
				(nonExistingReservationId))
		.thenThrow(NotFoundException.class);
		reservationService.getReservationById(nonExistingReservationId);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationResourceIdsNoStartTime() throws Exception {
		Mockito.when(mockReservationRepository
				.findAllResourceIdsByStartTimeAfterAndEndTimeBefore
				(null, endTime)).thenThrow(BadRequestException.class);
		reservationService.getReservationResourceIds(null, endTime);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationResourceIdsNoEndTime() throws Exception {
		Mockito.when(mockReservationRepository
				.findAllResourceIdsByStartTimeAfterAndEndTimeBefore
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
	public void saveReservationZeroUserId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, resourceId, 0, false, true);
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
