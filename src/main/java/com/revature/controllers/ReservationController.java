package com.revature.controllers;

import java.net.URI;
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

/**
 * The ReservationController communicates with the reservation service allowing
 * for access to the database. The controller selects a method by its mapping. 
 * @author 1811-Java-Nick 12/27/18
 */
@RestController
@RequestMapping("")
public class ReservationController {

	@Value("${RMS_RESOURCE_URL:http://localhost:8080/resources}")
	String uri;

	ReservationService reservationService;
	
	/**
	 * Used to construct a ReservationService service.
	 * @param reservationService The reservation service. 
	 */
	@Autowired
	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}
	
	/**
	 * Returns a list of resources based on the building's identification number.
	 * @param buildingId The identification number for a building.
	 * @return A list of resources. 
	 * @author Jaron 1811-Java-Nick 1/2/19
	 */
	private List<Resource> getResourcesByBuilding(int buildingId) {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Resource>> response = restTemplate.exchange(
				URI.create(this.uri + "/building/" + String.valueOf(buildingId)), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Resource>>() {
				});
		List<Resource> resources = response.getBody();
		return resources;
	}
	
	/**
	 * Returns a list of resources based on the campus identification number.
	 * @param campusId The identification number for a campus.
	 * @return A list of resources. 
	 * @author Jaron 1811-Java-Nick 1/2/19
	 */
	private List<Resource> getResourcesByCampus(int campusId) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Resource>> response = restTemplate.exchange(
				URI.create(this.uri + "/campus/" + String.valueOf(campusId)), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Resource>>() {
				});
		List<Resource> resources = response.getBody();
		return resources;
	}
	
	/**
	 * Returns a resource based on a resource identification number.
	 * @param id The identification number for a resource.
	 * @return A resource. 
	 * @author Jaron 1811-Java-Nick 1/2/19
	 */
	private Resource getResourceById(int id) {

		String idUri = Integer.toString(id);
		URI requestUri = URI.create(this.uri + "/" + idUri);

		RestTemplate restTemplate = new RestTemplate();
		Resource[] result = restTemplate.getForObject(requestUri, Resource[].class);
		return result[0];

	}
	
	/**
	 * Return a list of upcoming reservations if the user has any.
	 * Otherwise, return a list of all reservations for the user.
	 * @param id User that is logged in.
	 * @param upcoming If true, return a list of upcoming reservations. 
	 * 		  		   Else, return a list of all reservations.
	 * @return A list of reservations. 
	 */
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
	/**
	 * Returns a reservation based on a resource identification number.
	 * @param id The identification number for a resource.
	 * @return A reservation. 
	 */
	@GetMapping("id")
	public Reservation getReservationById(@RequestParam int id) {
		return reservationService.getReservationById(id);
	}
	
	/**
	 * Returns a list of all available resources between startTime and endTime,
	 * purpose, and whether there is a buildingId or not.
	 * @param startTime Initial time for resources.
	 * @param endTime Cutoff time for resources.
	 * @param purpose An enumeration, 0 is interview, 1 is panel.
	 * @param campusId The identification number for a campus.
	 * @param buildingId The identification number for a building.
	 * @return A list of resources. 
	 */
	@GetMapping("available")
	public List<Resource> getAvailableResources(@RequestParam String startTime, 
			@RequestParam String endTime,
			@RequestParam Purpose purpose, 
			@RequestParam Integer campusId,
			@RequestParam(required = false) Integer buildingId) {
		List<Resource> resources = new ArrayList<>();
		if (buildingId != null) {
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
	
	/**
	 * Persist a reservation in the database.
	 * @param reservation A reservation object to be persisted in the database.
	 * @return A reservation. 
	 */
	public Reservation saveReservation(@RequestBody Reservation reservation) {
		return reservationService.saveReservation(reservation);
	}
	
	/**
	 * Updates Reservation in database to "cancelled".
	 * @param id The identification number for a reservation.
	 * @return The id of the reservation that was updated. 
	 */
	@PostMapping("cancel")
	public int cancelReservation(@RequestParam int id) {
		return reservationService.cancelReservation(id);
	}
	
	/**
	 * Persists a reservation object in the database.
	 * @param reservationDTO The reservation object.
	 * @return A reservation. 
	 */
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
	
	/**
	 * Get a list of all reservations.
	 * @return A list of reservations. 
	 */
	@GetMapping("")
	public List<Reservation> getAll() {
		return reservationService.getAll();
	}

}
