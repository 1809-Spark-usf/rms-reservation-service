package com.revature.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.models.Reservation;
import com.revature.models.ReservationEmail;
import com.revature.models.Resource;
import com.revature.repositories.ReservationRepository;

/**
 * The Class ReservationService.
 * 
 * @author 1811-Java-Nick 12/27/18
 */
@Service
public class ReservationService {

	/** The reservation repository. */
	ReservationRepository reservationRepository;
	/** Using the user service to access the user repository */
	UserService userService;
	/** The time now. */
	static LocalDateTime timeNow = LocalDateTime.now();
	/** The URI for the Email Service */
	@Value("${RMS_EMAIL_URL:http://localhost:8080/email/}")
	String emailUri;
	@Value("${RMS_RESOURCE_URL:http://localhost:8080/resources}")
	String uri;

	/**
	 * Instantiates a new reservation service.
	 *
	 * @param reservationRepository
	 *            the reservation repository
	 */
	@Autowired
	public ReservationService(ReservationRepository reservationRepository, UserService userService) {
		super();
		this.reservationRepository = reservationRepository;
		this.userService = userService;
	}

	/**
	 * Gets the reservations by user id.
	 *
	 * @param id
	 *            the id
	 * @return the reservations by user id
	 */
	public List<Reservation> getReservationsByUserId(String id) {
		return reservationRepository.findByUserId(id);
	}

	/**
	 * Gets the upcoming reservations by user id.
	 *
	 * @param id
	 *            the id
	 * @return the upcoming reservations by user id
	 */
	public List<Reservation> getUpcomingReservationsByUserId(String id) {
		return reservationRepository.findAllByUserIdAndUpcoming(id, LocalDateTime.now());
	}

	/**
	 * Gets the past reservations by user id.
	 *
	 * @param id
	 *            the id
	 * @return the past reservations by user id
	 */
	public List<Reservation> getPastReservationsByUserId(String id) {
		return reservationRepository.findAllByUserIdAndPast(id, LocalDateTime.now());
	}

	/**
	 * Gets the reservation by id.
	 *
	 * @param id
	 *            the id
	 * @return the reservation by id
	 */
	public Reservation getReservationById(int id) {
		return reservationRepository.findById(id);
	}

	/**
	 * Gets the reservation resource ids.
	 *
	 * @param startDateTime
	 *            the start date time
	 * @param endDateTime
	 *            the end date time
	 * @return the reservation resource ids
	 */
	public List<Integer> getReservationResourceIds(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return reservationRepository.findResourceIdsByStartTimeAfterAndEndTimeBefore(startDateTime, endDateTime);
	}

	/**
	 * Persist reservation in the database.
	 *
	 * @param reservation
	 *            the reservation
	 * @return the reservation
	 */
	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	/**
	 * Cancel reservation by id.
	 *
	 * @param id
	 *            the id
	 * @return the int
	 */
	public int cancelReservation(int id) {
		return reservationRepository.updateCancelledById(id);
	}

	public Resource getResourceById(int id) {

		String idUri = Integer.toString(id);
		URI requestUri = URI.create(this.uri + "/" + idUri);

		RestTemplate restTemplate = new RestTemplate();
		Resource[] result = restTemplate.getForObject(requestUri, Resource[].class);
		return result[0];

	}
	/**
	 * Gets the reservation by criteria.
	 *
	 * @param reservation
	 *            the reservation
	 * @return the reservation by criteria
	 */
	public List<Reservation> getReservationByCriteria(Reservation reservation) {
		return reservationRepository.findAll(Example.of(reservation));
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public List<Reservation> getAll() {
		return reservationRepository.findAll();
	}

	/**
	 * Posts a ReservationEmail object to the Email service in order to send a
	 * confirmation email to the user
	 * 
	 * @param reservation
	 * @param resource
	 * @author Austin D. 1811-Java-Nick 1/4/19
	 */
	@HystrixCommand(fallbackMethod = "emailFallback")
	public void sendConfirmationToEmailService(Reservation reservation) {
		Resource resource = getResourceById(reservation.getResourceId());
		String buildingName = resource.getBuilding().getName();
		String resourceName = resource.getName();
		String userEmail = userService.findUserById(reservation.getUserId()).getEmail();
		ReservationEmail reservationEmail = new ReservationEmail(userEmail, reservation.getStartTime(),
				reservation.getEndTime(), buildingName, resourceName, reservation.getId(),
				reservation.getReminderTime());
	
		new RestTemplate().postForLocation(URI.create(emailUri + "sendconfirmation"), reservationEmail);
	}

	/**
	 * A fallback method for use with Hystrix using the Circuit Breaker pattern. If
	 * the email service fails or if it is down, the postConfirmation method will be
	 * replaced with this method call and the reservation service will still be able
	 * to run. Loosely couples the two services.
	 * 
	 * @param reservation
	 */
	private void emailFallback(Reservation reservation) {
	//This method is used as a fallback for sendConfirmationToEmailService
	}
	
	/**
	 * Posts a ReservationEmail object to the Email service in order to send a
	 * cancellation email to the user
	 * 
	 * @param reservation
	 * @param resource
	 * @author Austin D. 1811-Java-Nick 1/3/19
	 */
	@HystrixCommand(fallbackMethod = "cancelFallback")
	public void sendCancellationToEmailService(int reservationId) {
		Reservation reservation = getReservationById(reservationId);
		Resource resource = getResourceById(reservation.getResourceId());
		String userEmail = userService.findUserById(reservation.getUserId()).getEmail();
		String buildingName = resource.getBuilding().getName();
		String resourceName = resource.getName();
		ReservationEmail reservationEmail = new ReservationEmail(userEmail, reservation.getStartTime(),
				reservation.getEndTime(), buildingName, resourceName, reservation.getId(),
				reservation.getReminderTime());
		
		new RestTemplate().postForLocation(URI.create(emailUri + "sendcancellation"), reservationEmail);
		
	}
	
	/**
	 * A fallback method for use with Hystrix using the Circuit Breaker pattern. If
	 * the email service fails or if it is down, the postConfirmation method will be
	 * replaced with this method call and the reservation service will still be able
	 * to run. Loosely couples the two services.
	 * 
	 * @param reservationId
	 */
	private void cancelFallback(int reservationId) {
	//This method is used as a fallback for sendCancellationToEmailService
	}
}
