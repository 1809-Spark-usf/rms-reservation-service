package com.revature.tests.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ReservationController;
import com.revature.dtos.ReservationDto;
import com.revature.enumerations.Purpose;
import com.revature.models.Reservation;
import com.revature.models.Resource;
import com.revature.services.ReservationService;
import com.revature.services.UserService;

//@author 190122-Java-Evaine | 03/25/2019
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTests {

	private ObjectMapper om;

	private MockMvc mockMvc;

	@Mock
	private ReservationService reservationService;

	@Mock
	private UserService userService;

	@InjectMocks
	private ReservationController reservationController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
		om = new ObjectMapper();
	}

	@Test
	
	public void noReservationsMatchingUserGet() throws Exception {

		String badUserId = "This cannot possibly be a user ID";
		Mockito.when(reservationService.getUpcomingReservationsByUserId(badUserId))
				.thenThrow(new EntityNotFoundException());

		mockMvc.perform(
				get("/users").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isBadRequest());
	}

//	@Test affected by inner private method
//	public void foundReservationsMatchingUserGet() throws JsonProcessingException, Exception {
//		String goodUserId = "1";
//		ArrayList<Reservation> goodReservation = new ArrayList<>();
//		Reservation reservation = new Reservation();
//		goodReservation.add(reservation);
//
//		Mockito.when(reservationService.getReservationsByUserId(goodUserId)).thenReturn(goodReservation);
//
//		mockMvc.perform(get("/users?id=" + goodUserId).contentType(MediaType.APPLICATION_JSON_UTF8)
//				.accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isOk());
//	}

	@Test
	public void noReservationsMatchingIdGet() throws JsonProcessingException, Exception {
		int badReservationId = 5;

		Mockito.when(reservationService.getReservationById(anyInt())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		mockMvc.perform(get("/id?id=" + badReservationId).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(om.writeValueAsString(badReservationId)).accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void foundReservationsMatchingIdGet() throws JsonProcessingException, Exception {
		int goodReservationId = 2;
		Reservation goodReservation = new Reservation();
		ReservationDto goodObject = new ReservationDto();
		goodObject.setResourceId(goodReservationId);

		Mockito.when(reservationService.getReservationById(anyInt())).thenReturn(goodReservation);

		mockMvc.perform(get("/id?id=" + goodReservationId).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void noAvailableResourcesGet() throws Exception {
		mockMvc.perform(get("available").accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isNotFound());

	}

	
//	@Test affected by inner private method
//	public void foundAvailableResourcesBuildingGet() throws JsonProcessingException, Exception {
//		LocalDateTime currentTime = LocalDateTime.now();
//		LocalDateTime endTime = LocalDateTime.now();
//		Purpose purpose = Purpose.PANEL;
//		int campusId = 7;
//		int buildingId = 6;
//		Reservation goodReservation = new Reservation();
//
//		PowerMockito.doReturn(goodReservation).when(reservationController, "getResourcesByBuilding", buildingId);
//
//		mockMvc.perform(get("available?startTime=" + currentTime + "&endTime=" + endTime + "&purpose=" + purpose
//				+ "&campusId=" + campusId + "&buildingId=" + buildingId).contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON))
//				.andDo(print()).andExpect(status().isOk());
//
//	}
//
//	@Test affected by inner private method
//	public void foundAvailableResourcesCampusGet() throws JsonProcessingException, Exception {
//		LocalDateTime currentTime = LocalDateTime.now();
//		LocalDateTime endTime = LocalDateTime.now();
//		Purpose purpose = Purpose.PANEL;
//		int campusId = 7;
//		Reservation goodReservation = new Reservation();
//
//		PowerMockito.doReturn(goodReservation).when(reservationController, "getResourcesByCampus", campusId);
//
//		mockMvc.perform(get("available?startTime=" + currentTime + "&endTime=" + endTime + "&purpose=" + purpose
//				+ "&campusId=" + campusId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//				.andDo(print()).andExpect(status().isOk());
//
//	}

	@Test
	public void noReservationToCancel() throws Exception {
		
		mockMvc.perform(post("/cancel").contentType(MediaType.APPLICATION_JSON_UTF8).content("{}")
				.accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(handler().handlerType(ReservationController.class));

	}

	@Test
	public void successCancellingReservation() throws Exception {
		int id = 50;

		Mockito.when(reservationService.cancelReservation(id)).thenReturn(id);

		mockMvc.perform(post("/cancel?id=" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void failedDTOSavePost() throws Exception {

		Reservation badReservation = new Reservation();

		Mockito.when(reservationService.saveReservation(any(Reservation.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

		mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(om.writeValueAsString(badReservation)).accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void nullParameterForSaveResource() throws Exception {

		mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON_UTF8).content("{}")
				.accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isBadRequest());
	}

	
	@Ignore
	public void successfulDTOSavePost() throws JsonProcessingException, Exception {
		// setup all objects needed for the test
		// returns 400, content type not set
		// TODO: fix
		int idToSave = 19;
		ReservationDto reservation = new ReservationDto();
		Resource resource = new Resource();
		Purpose purpose = Purpose.INTERVIEW;
		reservation.setId(idToSave);
		reservation.setUserId("Aihonen");
		reservation.setStartTime(LocalDateTime.of(2019,5,10,15,42,0,0));
		reservation.setEndTime(LocalDateTime.of(2019,5,10,16,42,0,0));
		reservation.setResource(resource);
		reservation.setPurpose(purpose);
		
		
		System.out.println(reservation);

		byte[] result = om.writeValueAsBytes(reservation);

		Mockito.when(reservationService.saveReservation(any(Reservation.class))).thenReturn(new Reservation(reservation));

		mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(om.writeValueAsString(reservation)).accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated())
				.andExpect(content().bytes(result));
	}

}
