package com.revature.tests.services;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.revature.controllers.ReservationController;
import com.revature.models.Reservation;
import com.revature.services.ReservationService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={ReservationController.class})
//@WebAppConfiguration
public class ReservationControllerTests {

	ReservationService mockReservationService = mock(ReservationService.class);
//	ReservationController reservationController = (mockReservationRepository);
	LocalDateTime startTime = LocalDateTime.now();
	LocalDateTime endTime = LocalDateTime.now().plusHours(8);
	
	LocalDateTime noStartTime = null;
	LocalDateTime noEndTime = null;
	
	
	
	// insert appcontext
//	private WebApplicationContext webApplicationContext;

//	@Before
//	public void rest_assured_is_initialized_with_the_web_application_context_before_each_test() {
		// tell restassured to use the app context
//		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
//	}
	
	@Before public void
	given_rest_assured_is_configured_with_reservation_controller() {
		RestAssuredMockMvc.standaloneSetup(new ReservationController(mockReservationService, null));
	}

//	@After
//	public void rest_assured_is_reset_after_each_test() {
		// teardown restassured
//		RestAssuredMockMvc.reset();
//	}
	

//	@Test
//	public void getReservationsNoStartTime() {
//		given().
//			contentType(ContentType.JSON).
//			param("startTime", noStartTime).
//			param("endTime", endTime).
//		when().
//			get("/reservation").
//		then().
//			statusCode(400);	
//	}
//	
//	@Test
//	public void getReservationsNoEndTime() {
//		given().
//			contentType(ContentType.JSON).
//			param("startTime", startTime).
//			param("endTime", noEndTime).
//		when().
//			get("/reservation").
//		then().
//			statusCode(400);	
//	}
	
//	@Test
//	public void getReservationsGoodStartAndEndTime() {
//		List<Reservation> resList = new ArrayList<>();
//		resList.add(new Reservation());
//		
//		given().
////			accept(ContentType.JSON).
//			param("startTime", startTime).
//			body(endTime).
//		when().
//			get("/reservation").
//		then().
//			body(equalTo(resList));
//		
//	}


//	@Test
//	public void ReservationControllerTest() {
//		given().standaloneSetup(new ReservationController(reservationService)).param("startTime", startTime)
//				.param("endTime", endTime).when().get("").then().statusCode(200);
//
//	}
}
