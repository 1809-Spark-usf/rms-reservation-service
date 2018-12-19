package com.revature.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.revature.enumerations.Purpose;
import com.revature.enumerations.Type;
import com.revature.models.Reservation;
import com.revature.models.Resource;
import com.revature.services.ReservationService;

@RestController
@RequestMapping("")
public class ReservationController {
	
	@Value("${RMS_RESOURCE_URL:localhost:8080/resources}")
	String uri;
	
	ReservationService reservationService;

	@Autowired
	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}

	private List<Resource> getResourcesByBuilding(int buildingId) {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Resource>> response = restTemplate.exchange
				(this.uri + "/buildings/" + String.valueOf(buildingId), 
				HttpMethod.GET, 
				null,
				new ParameterizedTypeReference<List<Resource>>() {
				});
		List<Resource> resources = response.getBody();
		return resources;
	}
	
private List<Resource> getResourcesByCampus(int campusId) {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Resource>> response = restTemplate.exchange
				(this.uri + "/campus/" + String.valueOf(campusId), 
				HttpMethod.GET, 
				null,
				new ParameterizedTypeReference<List<Resource>>() {
				});
		List<Resource> resources = response.getBody();
		return resources;
	}

	private Resource getResourceById(int id) {

		String idUri = Integer.toString(id);
		String requestUri = this.uri + idUri;

		RestTemplate restTemplate = new RestTemplate();
		Resource[] result = restTemplate.getForObject(requestUri, Resource[].class);
		return result[0];

	}

	@GetMapping("users")
	public List<Reservation> getReservationsByUser(@RequestParam String id,
			@RequestParam(required = false) boolean upcoming) {
		if (upcoming) {
			List<Reservation> userList = reservationService.getUpcomingReservationsByUserId(id);
			for (Reservation reservation : userList) {
				reservation.setResource(getResourceById(reservation.getResourceId()));
			}
			return userList;

		}
		List<Reservation> userList = reservationService.getReservationsByUserId(id);
		for (Reservation reservation : userList) {
			reservation.setResource(getResourceById(reservation.getResourceId()));
		}
		return userList;
	}

	@GetMapping("id")
	public Reservation getReservationById(@RequestParam int id) {
		return reservationService.getReservationById(id);
	}

	@GetMapping("available")
	public List<Resource> getAvailableResources(
			@RequestParam String startTime, @RequestParam String endTime,
			@RequestParam Purpose purpose,
			@RequestParam Integer campusId,
			@RequestParam(required=false) Integer buildingId) {
		List<Resource> resources = new ArrayList<>();
		if(buildingId == null) {
			resources = getResourcesByBuilding(buildingId);
		} else {
			resources = getResourcesByCampus(campusId);
		}
		List<Integer> checkList = reservationService
				.getReservationResourceIds(LocalDateTime.parse(startTime),
				LocalDateTime.parse(endTime));

		for (int resourceId : checkList) {
			resources.removeIf(r -> r.getId() == resourceId);
		}

		if (purpose == Purpose.PANEL) {
			resources.removeIf(r -> r.getType() == Type.OFFICE);
		}
		return resources;
	}

	public Reservation saveReservation(@RequestBody Reservation reservation) {
		return reservationService.saveReservation(reservation);
	}

	@PostMapping("cancel")
	public int cancelReservation(@RequestParam int id) {
		return reservationService.cancelReservation(id);
	}

	@PostMapping("")
	public Reservation saveReservationsWithDTO(@RequestBody Reservation reservationDTO) {
		Reservation reservation = new Reservation();
		reservation.setPurpose(reservationDTO.getPurpose());
		reservation.setStartTime(reservationDTO.getStartTime());
		reservation.setEndTime(reservationDTO.getEndTime());
		reservation.setUserId(reservationDTO.getUserId());
		reservation.setResourceId(reservationDTO.getResourceId());
		reservation.setCancelled(reservationDTO.isCancelled());
		reservation.setApproved(reservationDTO.isApproved());
		return reservationService.saveReservation(reservation);
	}
	
	@GetMapping("")
	public List<Reservation> getAll() {
		return reservationService.getAll();
	}

}

