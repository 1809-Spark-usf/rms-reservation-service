package com.revature.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

	@Autowired
	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}

	private List<Resource> getResources() {
		final String uri = "http://localhost:4000/";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Resource>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Resource>>() {
				});
		List<Resource> resources = response.getBody();
		return resources;
	}

	private Resource getResourceById(int id) {

		String uri = "http://localhost:4000/";
		String idUri = Integer.toString(id);
		String requestUri = uri + idUri;

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
	public List<Resource> getAvailableResources(@RequestParam(required = false) String location,
			@RequestParam String startTime, @RequestParam String endTime,
			@RequestParam Purpose purpose) {

		List<Resource> resources = getResources();
		List<Integer> checkList = reservationService.getReservationResourceIds(LocalDateTime.parse(startTime),
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

	@PutMapping("cancel")
	public void cancelReservation(@RequestParam int id) {
		reservationService.cancelReservation(id);
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

