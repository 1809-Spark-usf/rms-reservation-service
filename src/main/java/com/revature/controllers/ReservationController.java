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
	ResourceClient resourceClient;

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
		System.out.println(resources);
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

	@GetMapping("resource")
	public List<Resource> getAvailableResources(@RequestParam(required = false) String location,
			@RequestParam String startDateTime, @RequestParam String endDateTime,
			@RequestParam Purpose purpose) {

		List<Resource> resources = getResources();
		List<Integer> checkList = reservationService.getReservationResourceIds(LocalDateTime.parse(startDateTime),
				LocalDateTime.parse(endDateTime));

		for (int resourceId : checkList) {
			resources.removeIf(r -> r.getId() == resourceId);
		}

		if (purpose == Purpose.PANEL) {
			resources.removeIf(r -> r.getType() == Type.OFFICE);
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

	// Authorization mapping
	@GetMapping("authorization")
	public static String getAccessToken(@RequestParam String code, HttpServletResponse response) throws IOException {
		
		final Map<String, String> env = System.getenv();

		final String client_id = env.get("SLACK_LOGIN");

		final String client_secret = env.get("SLACK_PASSWORD");

		String urlParameters = "code=" + code + "&" +

				"  redirect_uri=http://localhost:4200/loading&" +

				"  client_id=" + client_id + "&" +

				"  client_secret=" + client_secret;

		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		int postDataLength = postData.length;

		String request = "https://slack.com/api/oauth.access";

		URL url = new URL(request);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);

		conn.setInstanceFollowRedirects(false);

		conn.setRequestMethod("POST");

		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		conn.setRequestProperty("charset", "utf-8");

		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));

		conn.setUseCaches(false);

		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
			InputStream stream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
			String result = reader.readLine();
			String user = "";
			if (result.contains("\"ok\":false,")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return "";
			}
			for (int i = 0; i < result.length() - 4; i++) {
				if (result.charAt(i) == 'u' && result.substring(i, i + 4).equals("user")) {
					user += result.substring(i + 7, result.length());
					break;
				}
			}
			for (int i = 0; i < user.length() - 4; i++) {
				if (user.charAt(i) == 't' && user.substring(i, i + 4).equals("team")) {
					user = user.substring(0, i - 3);
					break;
				}
			}
			//response.sendError(HttpServletResponse.SC_OK);
			return user;
		} catch (IOException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return "";

		}

	}
	
//	public List<Reservation> getReservationsWithDTO(Reservation reservationDTO) {
//		Reservation reservation = new Reservation();
//		reservation.setId(reservationDTO.getId());
//		reservation.setPurpose(reservationDTO.getPurpose());
//		reservation.setUserId(reservationDTO.getUserId());
//		reservation.setCancelled(reservationDTO.isCancelled());
//		reservation.setApproved(reservationDTO.isApproved());
//		return reservationService.getReservationByCriteria(reservation);
//	}
	
//	@GetMapping("")
//	public List<Reservation> getAll() {
//		return reservationService.getAll();
//	}

}
