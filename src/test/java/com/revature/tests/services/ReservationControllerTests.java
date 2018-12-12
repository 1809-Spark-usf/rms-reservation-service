//package com.revature.tests.services;
//
//import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
//import static org.hamcrest.Matchers.hasItems;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.Mockito.mock;
//
//import java.time.LocalDateTime;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.revature.controllers.ReservationController;
//import com.revature.enumerations.Purpose;
//import com.revature.repositories.ReservationRepository;
//import com.revature.services.ReservationService;
//
//import io.restassured.http.ContentType;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(classes={ReservationController.class})
////@WebAppConfiguration
//public class ReservationControllerTests {
//	
//	ReservationService mockReservationService = mock(ReservationService.class);
//	@Autowired
//	ReservationRepository reservationRepository;
//	@Autowired
//	ReservationService reservationService = new ReservationService(reservationRepository);
////	ReservationController reservationController = (mockReservationService);
//	LocalDateTime startTime = LocalDateTime.now();
//	LocalDateTime endTime = LocalDateTime.now().plusHours(8);
//	
//	LocalDateTime someStartTime;
//	LocalDateTime someEndTime;
//	
//	LocalDateTime noStartTime = null;
//	LocalDateTime noEndTime = null;
//	
//	
//	
////	// insert appcontext
////	private WebApplicationContext webApplicationContext;
////
////	@Before
////	public void rest_assured_is_initialized_with_the_web_application_context_before_each_test() {
//////		 tell restassured to use the app context
////		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
////	}
////	
//	@Before public void
//	given_rest_assured_is_configured_with_reservation_controller() {
//		RestAssuredMockMvc.standaloneSetup(new ReservationController(reservationService));
//	}
////
////	@After
////	public void rest_assured_is_reset_after_each_test() {
//////		 teardown restassured
////		RestAssuredMockMvc.reset();
////	}
//	
//
//	@Test
//	public void getAvailableResourcesProducesCorrectResponse() {
//		given().
//			standaloneSetup(new ReservationController(reservationService)).
//			param("startDateTime", "2018-12-13T13:00:00").
//			param("endDateTime", "2018-12-13T15:00:00").
//			param("cxcv", "INTERVIEW").
//		when().
//			get("http://localhost:5000/resource").
//		then().
//			assertThat().
//			contentType(ContentType.JSON).
//			content("name", hasItems("Office 1"));
////			body("id", equalTo(3));	
//	}
////	
////	@Test
////	public void getReservationsNoEndTime() {
////		given().
////			contentType(ContentType.JSON).
////			param("startTime", startTime).
////			param("endTime", noEndTime).
////		when().
////			get("/reservation").
////		then().
////			statusCode(400);	
////	}
//	
////	@Test
////	public void getReservationsGoodStartAndEndTime() {
////		List<Reservation> resList = new ArrayList<>();
////		resList.add(new Reservation());
////		
////		given().
//////			accept(ContentType.JSON).
////			param("startTime", startTime).
////			body(endTime).
////		when().
////			get("/reservation").
////		then().
////			body(equalTo(resList));
////		
////	}
//
//
////	@Test
////	public void ReservationControllerTest() {
////		given().standaloneSetup(new ReservationController(reservationService)).param("startTime", startTime)
////				.param("endTime", endTime).when().get("").then().statusCode(200);
////
////	}
//}
