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
import org.springframework.web.bind.annotation.GetMapping;
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
	public ReservationController(ReservationService reservationService, ResourceClient resourceClient) {
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
			@RequestParam(required = false) boolean upcoming) {
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
	public int[] getReservations(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
		return reservationService.getReservationResourceIds(endTime, endTime);
	}

	@GetMapping("resource")
	public List<Resource> getAvailableResources(@RequestParam(required = false) String location,
			@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime,
			@RequestParam Purpose purpose) {

		List<Resource> resources = resourceClient.findResources();
		int[] checkList = reservationService.getReservationResourceIds(startDateTime, endDateTime);

		for (int resourceId : checkList) {
			for (Resource resource : resources) {
				if (resource.getId() == resourceId) {
					resources.remove(resource);
				}
			}
		}
		if (purpose == Purpose.PANEL) {
			for (Resource resource : resources) {
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

	// Authorization mapping
	@PostMapping("authorization")

	public static String getAccessToken(String code, HttpServletResponse response) throws IOException{

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

	conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

	conn.setRequestProperty("charset","utf-8");

	conn.setRequestProperty("Content-Length",Integer.toString(postDataLength));

	conn.setUseCaches(false);

	try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream()))
	{

	       wr.write( postData );

	       

	       InputStream stream = conn.getInputStream();

	       BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);

	       String result = reader.readLine();

	       String user = "";

		if(result.contains("false")){

			

			response.sendError(HttpServletResponse.SC_NOT_FOUND);

		

			return null;

		

		}

		

	       for(int i = 0; i < result.length() - 4; i++) {

	         

	         if(result.charAt(i) == 'u' && result.substring(i , i + 4).equals("user")) {

	           

	           user += result.substring(i + 7, result.length());

	           break;

	           

	         }

	         

	       }

	       for(int i = 0; i < user.length() - 4; i++) {

	         

	         if(user.charAt(i) == 't' && user.substring(i , i + 4).equals("team")) {

	           

	           user = user.substring(0, i - 3);

	           break;

	           

	         }

	         

	       }

	       

		response.setStatus(HttpServletResponse.SC_OK);

	       return user;

	    }catch(IOException e)
	{

		response.sendError(HttpServletResponse.SC_NOT_FOUND);

		return null;

	}

}

}
