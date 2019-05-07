package com.revature.controllers;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.revature.dtos.ReservationDto;
import com.revature.enumerations.Purpose;
import com.revature.enumerations.Type;
import com.revature.models.Reservation;
import com.revature.models.Resource;
import com.revature.services.ReservationService;
import com.revature.services.UserService;

/**
 * The ReservationController communicates with the reservation service allowing
 * for access to the database. The controller selects a method by its mapping.
 * 
 * @author 1811-Java-Nick 12/27/18
 */
@RestController
@RequestMapping("")
public class ReservationController {

	@Value("${RMS_RESOURCE_URL:http://localhost:8080/resources}")
	String uri;

	ReservationService reservationService;
	UserService userService;

	/**
	 * Used to construct a ReservationService service.
	 * 
	 * @param reservationService The reservation service.
	 */
	@Autowired
	public ReservationController(ReservationService reservationService, UserService userService) {
		super();
		this.reservationService = reservationService;
		this.userService = userService;
	}

	
	/**
	 * 
	 * 
	 */
	
	private static final String APPLICATION_NAME = "";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT
	 *            The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException
	 *             If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = ReservationController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(9001).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}



	/**
	 * Returns a list of resources based on the building's identification number.
	 * 
	 * @param buildingId The identification number for a building.
	 * @return A list of resources.
	 * @author Jaron 1811-Java-Nick 1/2/19
	 */
	private List<Resource> getResourcesByBuilding(int buildingId) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Resource>> response = restTemplate.exchange(
				URI.create(this.uri + "/building/" + buildingId), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Resource>>() {
				});
		return response.getBody();
	}

	/**
	 * Returns a list of resources based on the campus identification number.
	 * 
	 * @param campusId The identification number for a campus.
	 * @return A list of resources.
	 * @author Jaron 1811-Java-Nick 1/2/19
	 */
	private List<Resource> getResourcesByCampus(int campusId) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Resource>> response = restTemplate.exchange(URI.create(this.uri + "/campus/" + campusId),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Resource>>() {
				});
		return response.getBody();
	}

	/**
	 * Returns a resource based on a resource identification number.
	 * 
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
	 * Return a list of upcoming reservations if the user has any. Otherwise, return
	 * a list of all reservations for the user.
	 * 
	 * @param id       User that is logged in.
	 * @param upcoming If true, return a list of upcoming reservations. Else, return
	 *                 a list of all reservations.
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
	 * 
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
	 * 
	 * @param startTime  Initial time for resources.
	 * @param endTime    Cutoff time for resources.
	 * @param purpose    An enumeration, 0 is interview, 1 is panel.
	 * @param campusId   The identification number for a campus.
	 * @param buildingId The identification number for a building.
	 * @return A list of resources.
	 */
	@GetMapping("available")
	public List<Resource> getAvailableResources(@RequestParam String startTime, @RequestParam String endTime,
			@RequestParam Purpose purpose, @RequestParam Integer campusId,
			@RequestParam(required = false) Integer buildingId) {
		List<Resource> resources;
		if (buildingId != null) {
			resources = getResourcesByBuilding(buildingId);
		} else {
			resources = getResourcesByCampus(campusId);
		}

		List<Integer> checkList = reservationService.getReservationResourceIds(LocalDateTime.parse(startTime),
				LocalDateTime.parse(endTime));


		//Formatter to convert Times from Strings to LocalDateTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		//Converted values
		LocalDateTime start = LocalDateTime.parse(startTime, formatter);
		LocalDateTime end = LocalDateTime.parse(endTime, formatter);
		

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
	 * 
	 * @param reservation A reservation object to be persisted in the database.
	 * @return A reservation.
	 */
	public Reservation saveReservation(@RequestBody Reservation reservation) {
		return reservationService.saveReservation(reservation);
	}

	/**
	 * Updates Reservation in database to "cancelled".
	 * 
	 * @param id The identification number for a reservation.
	 * @return The id of the reservation that was updated.
	 */
	@PostMapping("cancel")
	public int cancelReservation(@RequestParam int id) {

		//reservationService.sendCancellationToEmailService(id);

		return reservationService.cancelReservation(id);
	}

	/**
	 * Persists a reservation object in the database.
	 * 
	 * @param reservationDTO The reservation object.
	 * @return A reservation.

	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Reservation saveReservationsWithDTO(@RequestBody ReservationDto reservationDto) throws GeneralSecurityException, IOException {
		if (reservationDto.getUserId() == null || reservationDto.getUserId().equals(""))
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		Reservation reservation = new Reservation(reservationDto);
		System.out.println(reservation);
		//reservationService.sendConfirmationToEmailService(reservation);
		//save to Google calendar
		// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
		
        Event event = new Event()
        	    .setSummary("RMS-RESERVATION")
        	    .setLocation(reservation.getResource().toString())
        	    .setDescription(reservation.getPurpose().toString());

        	DateTime startDateTime = new DateTime(reservation.getStartTime().toString()+":00-07:00");
        	EventDateTime start = new EventDateTime()
        	    .setDateTime(startDateTime)
        	    .setTimeZone("America/New_York");
        	event.setStart(start);

        	DateTime endDateTime = new DateTime(reservation.getEndTime().toString()+":00-07:00");
        	EventDateTime end = new EventDateTime()
        	    .setDateTime(endDateTime)
        	    .setTimeZone("America/New_York");
        	event.setEnd(end);

        	String calendarId = "primary";
        	event = service.events().insert(calendarId, event).execute();
        	System.out.printf("Event created: %s\n", event.getHtmlLink());
        	
        	
	
		return reservationService.saveReservation(reservation);
	}

	/**
	 * Get a list of all reservations.
	 * 
	 * @return A list of reservations.
	 */
	@GetMapping("")
	public List<Reservation> getAll() {
		return reservationService.getAll();
	}

	@ExceptionHandler
	public ResponseEntity<String> handleHttpClientException(HttpClientErrorException e) {
		String message = e.getMessage();
		return ResponseEntity.status(e.getStatusCode()).body(message);
	}
	
	@PostMapping("edit")
	@ResponseStatus (HttpStatus.CREATED)
		public Reservation EditReservation (@RequestBody Reservation res ) {
			//Reservation reservation = new Reservation(reservationDto);
			Reservation reservation = reservationService.getReservationById(res.getId());
			reservation.setStartTime(res.getStartTime());
			reservation.setResource(res.getResource());
			reservation.setReminderTime(res.getReminderTime());
			reservation.setPurpose(res.getPurpose());
			reservation.setEndTime(res.getEndTime());
			return reservationService.saveReservation(reservation);
			
	}
	
}
