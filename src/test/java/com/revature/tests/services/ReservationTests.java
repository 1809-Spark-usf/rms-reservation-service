package com.revature.tests.services;

import org.junit.Test;

import com.revature.exceptions.NotFoundException;
import com.revature.repositories.ReservationRepository;
import com.revature.services.ReservationService;

public class ReservationTests {
	
	ReservationRepository mockReservationRepository;
	ReservationService reservationService = new ReservationService(mockReservationRepository);
	
	@Test(expected=NullPointerException.class)
	public void getReservationNoUserEmail() throws Exception {
		reservationService.getReservationByUserEmail(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void getReservationNoReservations() throws Exception {
		reservationService.getReservationByUserEmail("emailThatHasNoReservations");
	}
	
	
	
}
