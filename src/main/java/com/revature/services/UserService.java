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
import com.revature.exceptions.BadRequestException;
import com.revature.models.SlackDto;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

/**
 * Contains Slack Response object class and methods for managing the Slack API login and token access.  
 * @author Clay
 *
 */
@Service
public class UserService {
	
	UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	/**
	 * Checks token (from cookie on the front end) to see 
	 * if it matches with the entry in the database and is not over 2 weeks old.
	 * @param token
	 * @return
	 * @throws Exception
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
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public User login(String code) throws Exception {
		final Map<String, String> env = System.getenv();
		final String client_id = env.get("SLACK_LOGIN");
		final String client_secret = env.get("SLACK_PASSWORD");

		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://slack.com/api/oauth.access";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("code", code);
		map.add("redirect_uri", "http://localhost:4200/loading");
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
        
        if (slackResponse.error != null ) {
        	throw new BadRequestException("Login Failed!");
        }
        // Gets the user, adds a token expiration date as 2 weeks from today, and
        // then generates a token to be saved by the front end in local storage.
        User resultUser = slackResponse.getUser();
        resultUser.setExpiration(LocalDate.now().plusWeeks(2));
        resultUser.setToken(resultUser.getId() + "." + (int)(Math.random() * 10000000));
        // Saves or updates user in the database
        userRepository.saveAndFlush(resultUser);
        
        return resultUser;
        // Add user's last login to database, then return the user.
		
		/////////////////////////////////////////////////////////////////////
        // Ben's Original implementation (in case last minute changes above fail)
        /////////////////////////////////////////////////////////////////////
//		final Map<String, String> env = System.getenv();
//		final String client_id = env.get("SLACK_LOGIN");
//		final String client_secret = env.get("SLACK_PASSWORD");
//		String urlParameters = "code=" + code + "&" +
//			"  redirect_uri=http://localhost:4200/loading&" +
//			"  client_id=" + client_id + "&" +
//			"  client_secret=" + client_secret;
//		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
//		int postDataLength = postData.length;
//		String request = "https://slack.com/api/oauth.access";
//		URL url = new URL(request);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setDoOutput(true);
//		conn.setInstanceFollowRedirects(false);
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		conn.setRequestProperty("charset", "utf-8");
//		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
//		conn.setUseCaches(false);
//		
//		String result;
//		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
//			wr.write(postData);
//			InputStream stream = conn.getInputStream();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
//			result = reader.readLine();
//		} catch (IOException e) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return "";
//		}
//		// Manually parses the response.
//			// If response has "ok": false, then we know the authorization failed.
//		if (result.contains("\"ok\":false,")) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return "";
//		}
//		// Manually concatenate the response as a JSON string.
//		String user = "{";
//		for (int i = 0; i < result.length() - 4; i++) {
//			if (result.charAt(i) == 'u' && result.substring(i, i + 4).equals("user")) {
//				user += result.substring(i + 7, result.length());
//				break;
//			}
//		}
//		for (int i = 0; i < user.length() - 4; i++) {
//			if (user.charAt(i) == 't' && user.substring(i, i + 4).equals("team")) {
//				user = user.substring(0, i - 3);
//				break;
//			}
//		}
//		user += "}";
//		return user;

	}
	
	

}
