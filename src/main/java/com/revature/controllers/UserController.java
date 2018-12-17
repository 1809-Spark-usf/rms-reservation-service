package com.revature.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.BadRequestException;
import com.revature.models.Reservation;
import com.revature.models.User;
import com.revature.services.UserService;


@RestController
@RequestMapping("users")
public class UserController {
	
	UserService userService;

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
	public User login(@RequestParam String code, HttpServletResponse response) throws Exception {
		return this.userService.login(code);
	}
	
	/**
	 * Checks if the user has been authorized by slack API recently.
	 * @param token (from cookie)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("rememberme")
	public User rememberMe(@RequestParam String token) throws Exception {
		return this.userService.checkToken(token);
	}
	
	@GetMapping("logout")
	public void logout(@RequestParam String token) throws Exception {
		this.userService.logout(token);
	}
	
	@GetMapping("calendar")
	public String authorizeCalendar(@RequestParam String token, @RequestParam Reservation reservation) throws Exception {
		return this.userService.authorizeCalendar(token, reservation);
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String badRequest(BadRequestException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String badRequest(Exception e) {
		return "Server Exception";
	}
	
	
}
