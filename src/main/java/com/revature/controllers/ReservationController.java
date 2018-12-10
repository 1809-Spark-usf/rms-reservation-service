package com.revature.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.feign.ResourceClient;
import com.revature.models.Reservation;
import com.revature.models.Resource;
import com.revature.purposeEnum.Purpose;
import com.revature.services.ReservationService;

@RestController
@RequestMapping("reservations")
public class ReservationController {
	
	ReservationService reservationService;
	ResourceClient resourceClient;
	
	@Autowired
	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}
	
	
//	@GetMapping("")
//	public List<Reservation> getReservations(@RequestParam int id,
//			@RequestParam Purpose purpose, @RequestParam String userEmail,
//			@RequestParam boolean cancelled, @RequestParam boolean approved) {
//		return reservationService.getReservationByCriteria(id, purpose, 
//				userEmail, cancelled, approved);
//	}
	
	@GetMapping("")
	public List<Reservation> getAll() {
		return reservationService.getAll();
	}
	
	@GetMapping("users")
	public List<Reservation> getReservationsByUserUpcoming(@RequestParam int id,
			@RequestParam boolean upcoming) {
		if (upcoming) {
			List<Reservation> userList = reservationService.getUpcomingReservationsByUserId(id);
			for (Reservation reservation: userList) {
				reservation.setResource(resourceClient.getResourceById(reservation.getResourceID()));
			}
			return userList;
			
		} else if (upcoming == false) {
			List<Reservation> userList = reservationService.getPastReservationsByUserId(id);
			for (Reservation reservation: userList) {
				reservation.setResource(resourceClient.getResourceById(reservation.getResourceID()));
			}
			return userList;
		}
		List<Reservation> userList = reservationService.getReservationsByUserId(id);
		for (Reservation reservation: userList) {
			reservation.setResource(resourceClient.getResourceById(reservation.getResourceID()));
		}
		return userList;
	}
	
	@GetMapping("{id}")
	public Reservation getReservationById(@PathVariable int id) {
		return reservationService.getReservationById(id);
	}
	
	@GetMapping("reservations")
	public List<Reservation> getReservationsWithDTO(Reservation reservationDTO) {
		Reservation reservation = new Reservation();
		reservation.setId(reservationDTO.getId());
		reservation.setPurpose(reservationDTO.getPurpose());
		reservation.setUserId(reservationDTO.getUserId());
		reservation.setCancelled(reservationDTO.isCancelled());
		reservation.setApproved(reservationDTO.isApproved());
		return reservationService.getReservationByCriteria(reservation);
	}
	
	@GetMapping("available")
	public int[] getReservations(@RequestParam LocalDateTime startTime,
			@RequestParam LocalDateTime endTime) {
		return reservationService.getReservationResourceIds(endTime, endTime);
	}
	
	@GetMapping("resources")
	public List<Resource> getAvailableResources(
			@RequestParam String location,
			@RequestParam LocalDateTime startDateTime,
			@RequestParam LocalDateTime endDateTime,
			@RequestParam Purpose purpose) {
			
		// Feign Client send query for resources at location
			List<Resource> resources = new ArrayList<>();
			int[] checkList = reservationService.getReservationResourceIds
					(startDateTime, endDateTime);
			
			for (int resourceId: checkList) {
				for (Resource resource: resources) {
					if (resource.getId() == resourceId) {
						resources.remove(resource);
					}
				}
			}
			return resources;
	}
	
	@PostMapping("")
	public Reservation saveReservation(@RequestParam Reservation reservation) {
		return reservationService.saveReservation(reservation);
	}
	
	@PutMapping("cancel")
	public void cancelReservation(@RequestParam int id) {
		reservationService.cancelReservation(id);
	}
	

	
}
