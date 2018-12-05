package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Reservation;
import com.revature.purposeEnum.Purpose;
import com.revature.services.ReservationService;

@RestController
public class ReservationController {
	
	ReservationService reservationService;
	
	@Autowired
	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}
	
	
	@GetMapping("")
	public List<Reservation> getReservations(@RequestParam int id,
			@RequestParam Purpose purpose, @RequestParam String userEmail,
			@RequestParam boolean cancelled, @RequestParam boolean approved) {
		return reservationService.getReservationByCriteria(id, purpose, 
				userEmail, cancelled, approved);
	}



	
}
