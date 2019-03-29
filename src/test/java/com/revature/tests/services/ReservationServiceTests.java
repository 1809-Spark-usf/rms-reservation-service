package com.revature.tests.services;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.enumerations.Purpose;
import com.revature.exceptions.BadRequestException;
import com.revature.exceptions.NotFoundException;
import com.revature.models.Reservation;
import com.revature.repositories.ReservationRepository;
import com.revature.services.ReservationService;
import com.revature.services.UserService;

//@author 190122-Java-Evaine | 03/25/2019
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ReservationServiceTests {		
	@Mock
	private ReservationRepository reservationRepository;
	@Mock
	private UserService userService;
	
	@InjectMocks
	ReservationService reservationService;
	
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
		Mockito.when(reservationRepository.findByUserId(null))
		.thenThrow(BadRequestException.class);
		reservationService.getReservationsByUserId(null);
	}
	
	@Test(expected=NotFoundException.class)
	public void getReservationsByUserIdNotFound() throws Exception {
		Mockito.when(reservationRepository.findByUserId(nonExistingUserId))
		.thenThrow(NotFoundException.class);
		reservationService.getReservationsByUserId(nonExistingUserId);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationResourceIdsNoStartTime() throws Exception {
		Mockito.when(reservationRepository
				.findResourceIdsByStartTimeAfterAndEndTimeBefore
				(null, endTime)).thenThrow(BadRequestException.class);
		reservationService.getReservationResourceIds(null, endTime);
	}
	
	@Test(expected=BadRequestException.class)
	public void getReservationResourceIdsNoEndTime() throws Exception {
		Mockito.when(reservationRepository
				.findResourceIdsByStartTimeAfterAndEndTimeBefore
				(startTime, null)).thenThrow(BadRequestException.class);
		reservationService.getReservationResourceIds(startTime, null);
	}	
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoPurpose() throws Exception {
		Reservation badReservation = new Reservation
				(0, null, startTime, endTime, null, resourceId, userId, false, true,1);
		Mockito.when(reservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoStartTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, null, endTime, null, resourceId, userId, false, true,1);
		Mockito.when(reservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoEndTime() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, null, null, resourceId, userId, false, true,1);
		Mockito.when(reservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationZeroResourceId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, 0, userId, false, true,1);
		Mockito.when(reservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNotFoundResourceId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, nonExisitingResourceId, userId, false, true,1);
		Mockito.when(reservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNoUserId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, resourceId, null, false, true,1);
		Mockito.when(reservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void saveReservationNotFoundUserId() throws Exception {
		Reservation badReservation = new Reservation
				(0, purpose, startTime, endTime, null, resourceId, nonExistingUserId, false, true,1);
		Mockito.when(reservationRepository.save(badReservation)).thenThrow(BadRequestException.class);
		reservationService.saveReservation(badReservation);
	}
	
	@Test(expected=BadRequestException.class)
	public void cancelReservationIdZero() throws Exception {
		Mockito.when(reservationRepository.updateCancelledById(0)).thenThrow(BadRequestException.class);
		reservationService.cancelReservation(0);
	}
	
	@Test(expected=NotFoundException.class)
	public void cancelReservationIdNotExist() throws Exception {
		Mockito.when(reservationRepository.updateCancelledById(nonExisitingResourceId)).thenThrow(NotFoundException.class);
		reservationService.cancelReservation(0);	
	}
		
}
