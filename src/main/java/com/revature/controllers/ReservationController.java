package com.revature.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.enumerations.Purpose;
import com.revature.enumerations.Type;
import com.revature.feign.ResourceClient;
import com.revature.models.Reservation;
import com.revature.models.Resource;
import com.revature.services.ReservationService;

@RestController
@RequestMapping("")
public class ReservationController {
	
	ReservationService reservationService;
	ResourceClient resourceClient;
	
	@Autowired
	public ReservationController(ReservationService reservationService,
			ResourceClient resourceClient) {
		super();
		this.reservationService = reservationService;
		this.resourceClient = resourceClient;
	}
	
//	@GetMapping("")
//	public List<Reservation> getReservations(@RequestParam int id,
//			@RequestParam Purpose purpose, @RequestParam String userEmail,
//			@RequestParam boolean cancelled, @RequestParam boolean approved) {
//		return reservationService.getReservationByCriteria(id, purpose, 
//				userEmail, cancelled, approved);
//	}
	
	@GetMapping("/feign")
	public List<Resource> getAllR() {
		return resourceClient.findResources();
	}
	
	@GetMapping("")
	public List<Reservation> getAll() {
		return reservationService.getAll();
	}
	
	@GetMapping("users")
	public List<Reservation> getReservationsByUser(@RequestParam String id,
			@RequestParam(required=false) boolean upcoming) {
		if (upcoming) {
			List<Reservation> userList = reservationService.getUpcomingReservationsByUserId(id);
//			for (Reservation reservation: userList) {
//				reservation.setResource(resourceClient.getResourceById(reservation.getresourceId()));
//			}
			return userList;
			
		}
		List<Reservation> userList = reservationService.getReservationsByUserId(id);
//		for (Reservation reservation: userList) {
//			reservation.setResource(resourceClient.getResourceById(reservation.getresourceId()));
//		}
		return userList;
	}
	
	@GetMapping("id")
	public Reservation getReservationById(@RequestParam int id) {
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
	
	@GetMapping("resource")
	public List<Resource> getAvailableResources(
			@RequestParam(required=false) String location,
			@RequestParam LocalDateTime startDateTime,
			@RequestParam LocalDateTime endDateTime,
			@RequestParam Purpose purpose) {
			
		
			List<Resource> resources = resourceClient.findResources();
			int[] checkList = reservationService.getReservationResourceIds
					(startDateTime, endDateTime);
			
			for (int resourceId: checkList) {
				for (Resource resource: resources) {
					if (resource.getId() == resourceId) {
						resources.remove(resource);
					}
				}
			}
			if (purpose == Purpose.PANEL) {
				for (Resource resource: resources) {
					if (resource.getType() == Type.OFFICE) {
						resources.remove(resource);
					}
				}
			}
			return resources;
	}
	
	@PostMapping("")
	public Reservation saveReservation(@RequestBody Reservation reservation) {
		return reservationService.saveReservation(reservation);
	}
	
	@PutMapping("cancel")
	public void cancelReservation(@RequestParam int id) {
		reservationService.cancelReservation(id);
	}
	
}
