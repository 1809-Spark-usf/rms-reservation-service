package com.revature.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.ReservationDto;
import com.revature.exceptions.BadRequestException;
import com.revature.models.Reservation;
import com.revature.models.User;
import com.revature.services.UserService;

/**
 * The UserController communicates with the User service allowing
 * for access to the database. The controller selects a method by its mapping. 
 * @author 1811-Java-Nick 12/27/18
 */
@RestController
@RequestMapping("users")
public class UserController {
	
	UserService userService;
	
	/**
	 * Used to construct a userService service.
	 * @param reservationService The reservation service. 
	 */
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	/**
	* Authorizes users by slack API
	* @param code - The code from Slack API login
	* @return - token
	 * @throws Exception to me handled by exception handler with a status code
	*/
	@GetMapping("authorization")
	public User login(@RequestParam String code, HttpServletResponse response) {
		return this.userService.login(code);
	}
	
	/**
	 * Checks if the user has been authorized by slack API recently.
	 * @param token (from cookie)
	 * @return requested token if it exists
	 * @throws Exception
	 */
	@GetMapping("rememberme")
	public User rememberMe(@RequestParam String token) {
		return this.userService.checkToken(token);
	}
	/**
	 * method that request a token from the current user and logs the user
	 * out of the current session
	 * @param token unique identifier from the user used to expire the 
	 * 				local user session
	 * @throws Exception
	 */
	@GetMapping("logout")
	public void logout(@RequestParam String token) {
		this.userService.logout(token);
	}
	/**
	 * Send a user token and a reservation object to a google calendar API so it can 
	 * save a date on the user's google calendar
	 * @param token unique value to identify the user
	 * @param reservation object gotten from the session that define the users intentions
	 * @return the string of the HTTP ResponseEntity 
	 * @throws Exception
	 */
	@GetMapping("calendar")
	public String authorizeCalendar(@RequestParam String token, @RequestParam ReservationDto reservationDto)  {
		return this.userService.authorizeCalendar(token, new Reservation(reservationDto));
	}
	
	/**
	 * Handles a bad request exception and returns the message of the exception itself
	 * @param e the exception
	 * @return Returns the detail message string of this throwable.
	 */
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String badRequest(BadRequestException e) {
		return e.getMessage();
	}
	
	/**
	 * Handles internal server errors 
	 * @param e the exception
	 * @return Returns the detail message string of this throwable.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String badRequest(Exception e) {
		return "Server Exception";
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") String id) {
		return this.userService.findUserById(id);
	}
	
	
}
