package com.revature.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.GoogleDto;
import com.revature.dtos.SlackDto;
import com.revature.exceptions.BadRequestException;
import com.revature.models.Reservation;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

/**
 * Contains Slack Response object class and methods for managing the Slack API login and token access.  
 * @author 1811-Java-Nick 12/27/18
 *
 */
@Service
public class UserService {
	
	public UserService() {
		super();
	}

	/** The user repository. */
	UserRepository userRepository;

	/**
	 * Instantiates a new user service.
	 *
	 * @param userRepository the user repository
	 */
	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	/**
	 * Checks token (from cookie on the front end) to see 
	 * if it matches with the entry in the database and is not over 2 weeks old.
	 *
	 * @param token the token
	 * @return the user
	 * @throws Exception the exception
	 */
	public User checkToken(String token) throws Exception {
		User user = userRepository.findByTokenAndExpirationAfter(token, LocalDate.now()).orElse(null);
		if (user == null ) {
			throw new BadRequestException("Credentials not found!");
		}
		return user;
	}
	
	/**
	 * Logs out user by setting expiration date to now.
	 *
	 * @param token the token
	 */
	@Transactional
	public void logout(String token) {
		User user = userRepository.findByToken(token).orElse(null);
		if (user != null) {
			user.setExpiration(LocalDate.now());
			userRepository.save(user);
		}
	}

	
	/**
	 * Logs user in by requesting the Slack API for information.
	 *  Note: Token should be updated to some kind of hashed code.
	 *  Currently, it would be very easy to break in to if you had a user's slack ID.
	 *
	 * @param code the code
	 * @return the user
	 * @throws Exception the exception
	 */
	public User login(String code) throws Exception {
		final Map<String, String> env = System.getenv();
		final String client_id = env.get("REFORCE_SLACK_CLIENT_ID");
		final String client_secret = env.get("REFORCE_SLACK_CLIENT_SECRET");

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://slack.com/api/oauth.access";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("code", code);
//		map.add("redirect_uri", "http://localhost:4200/loading");
		map.add("redirect_uri", env.get("REFORCE_WEBCLIENT_URL") + "loading");
		map.add("client_id", client_id);
		map.add("client_secret", client_secret);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		// Make the request
		ResponseEntity<String> result = restTemplate.postForEntity( url, request , String.class );
		// If login fails, throw exception
		 if (!result.getStatusCode().is2xxSuccessful()) {
				throw new BadRequestException("Slack API connection error");
		 }
		 // Handle the response body.
        String resultBody = result.getBody();
        SlackDto slackResponse;
        try {
        	// Map response to string (Slack response has a lot of details, we only need User details)
	        ObjectMapper objectMapper = new ObjectMapper();
	        slackResponse = objectMapper.readValue(resultBody, new TypeReference<SlackDto>(){});
		} catch (IOException e) {
			throw new BadRequestException("Mapping problem");
		}
        
        if (slackResponse.getError() != null ) {
        	System.out.println(slackResponse.getError());
        	throw new BadRequestException("Login Failed!");
        }
        // Gets the user, adds a token expiration date as 2 weeks from today, and
        // then generates a token to be saved by the front end in local storage.
        User resultUser = slackResponse.getUser();
        resultUser.setExpiration(LocalDate.now().plusWeeks(2));
        resultUser.setToken(resultUser.getId() + "." + (int)(Math.random() * 10000000));
        // Saves or updates user in the database with last login
        userRepository.saveAndFlush(resultUser);
        
        return resultUser;
	}

	/**
	 * Authorize calendar.
	 *
	 * @param code the code
	 * @param reservation the reservation
	 * @return the string
	 */
	public String authorizeCalendar(String code, Reservation reservation) {
		final Map<String, String> env = System.getenv();
		final String client_id = env.get("GOOGLE_CLIENT_ID");
		final String client_secret = env.get("GOOGLE_SECRET");

		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://www.googleapis.com/oauth2/v4/token";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("code", code);
//		map.add("redirect_uri", "http://localhost:4200/loading");
		map.add("client_id", client_id);
		map.add("client_secret", client_secret);
		map.add("grant_type", "authorization_code");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> result = restTemplate.postForEntity( url, request , String.class );
		// If login fails, throw exception
		 if (!result.getStatusCode().is2xxSuccessful()) {
				throw new BadRequestException("Google API connection error");
		 }
		 // Handle the response body -- we need to get the token out of it.
        String resultBody = result.getBody();
        GoogleDto googleResponse;
        try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        googleResponse = objectMapper.readValue(resultBody, new TypeReference<GoogleDto>(){});
		} catch (IOException e) {
			e.printStackTrace();
			throw new BadRequestException("Mapping problem");
		}
        System.out.println(googleResponse);
        if (googleResponse.getAccess_token() == null ) {
        	throw new BadRequestException("Login Failed!");
        }
        
        /*Now we have the token, we can construct the response
         For simplicity and in the interest of time, this will be created as a concatenated string.
        
         Gets the user, adds a token expiration date as 2 weeks from today, and
         then generates a token to be saved by the front end in local storage.
         POST TO:
        	https://www.googleapis.com/calendar/v3/calendars/calendarId/events
        	"Authorization: Bearer your_auth_token
         Host: www.googleapis.com
         Content-Type: multipart/mixed; boundary=batch_foobarbaz
         Content-Length: total_content_length*/
        
         String event = "{\"end\": { \"dateTime\": \"" + reservation.getEndTime() + "\"}, "
         		+ "\"start\": { \"dateTime\": \"" + reservation.getStartTime() + "\"}, "
         		+ "\"description\": \" Revature " + reservation.getPurpose().toString().toLowerCase() + " in " + reservation.getResource().getName()
         		+ "}";
         
 		String eventUrl = "https://www.googleapis.com/calendar/v3/calendars/calendarId/events";
		
 		RestTemplate googleRestTemplate = new RestTemplate();
 		
 		HttpHeaders eventHeaders = new HttpHeaders();
 		headers.add("Authorization", "Bearer " + googleResponse.getAccess_token());
 		headers.setContentType(MediaType.APPLICATION_JSON);
 
		HttpEntity<String> googleRequest = new HttpEntity<String>(event, headers);
 		ResponseEntity<String> eventResult = restTemplate.postForEntity( eventUrl, event, String.class );
 		
 		return eventResult.getBody();
	}
	
	public User findUserById(String id) {
		return userRepository.findUserById(id);
	}
	
}
