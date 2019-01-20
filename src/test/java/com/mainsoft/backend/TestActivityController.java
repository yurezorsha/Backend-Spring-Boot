package com.mainsoft.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mainsoft.backend.controller.AuthController;
import com.mainsoft.backend.entity.Activity;
import com.mainsoft.backend.entity.Login;
import com.mainsoft.backend.security.jwt.JwtResponse;
import com.mainsoft.backend.service.ActivityService;

/**
 * integration tests for Activitycontroller
 * 
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value= {"/testdb/create-test-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value= {"/testdb/delete-test-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class TestActivityController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private ActivityService activityService;
		
	private String token;
	
	public void setUp() {
		Login login = new Login();
		login.setUsername("valera");
		login.setPassword("valera");
		
		JwtResponse response = (JwtResponse) authController.authenticateUser(login).getBody();
		
		token = response.toString();		
	
	}
	
	
	
	

	@Test
	public void testGetActivityWithOutAuthorizationToken() throws Exception {
		
		mockMvc.perform(get("/api/activity/{id}",1)
				   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				   .andExpect(status().isUnauthorized());
		
	}
	
	@Test
	public void testGetActivityWithIncorrectId() throws Exception {
		setUp();
		
		mockMvc.perform(get("/api/activity/{id}",100000)
				   .header("Authorization", token)
				   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				   .andExpect(status().isNotFound());
		
	}
	
	@Test
	public void testGetActivityWithAuthorizationToken() throws Exception {
		
		setUp();
		
		Activity activity = activityService.getActivityById(1L); 
		
		mockMvc.perform(get("/api/activity/{id}",1)
				   .header("Authorization", token)
				   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				   .andExpect(status().isOk())
				   .andExpect(jsonPath("$.id").value(activity.getId()))
				   .andExpect(jsonPath("$.distance").value(activity.getDistance()))
				   .andExpect(jsonPath("$.runTime").value(activity.getRunTime()))
				   .andExpect(jsonPath("$.runDate").isNotEmpty());
				  
				  			 
		
	}
	
	@Test
	public void testGetAllActivityByUserName() throws Exception {
		
		setUp();
		mockMvc.perform(get("/api/activities/{username}","valera")
				   .header("Authorization", token)
				   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				   .andExpect(status().isOk());
				   
				  
				  					
	}
	
	@Test
	public void testPostActivityByUserName() throws Exception {
		
		setUp();
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = simpleDateFormat.parse("2019-01-28");
		
		Activity activity = new Activity();
		activity.setDistance(7.5F);
		activity.setRunDate(date);
		activity.setRunTime(50);
		
		mockMvc.perform(post("/api/activity/{username}","valera")
				   .header("Authorization", token)
				   .content(JsonUtil.toJson(activity))
				   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				   .andExpect(status().isCreated())
				   .andExpect(jsonPath("$.id").isNotEmpty())
				   .andExpect(jsonPath("$.distance").value(activity.getDistance()))
				   .andExpect(jsonPath("$.runTime").value(activity.getRunTime()))
				   .andExpect(jsonPath("$.runDate").value(simpleDateFormat.format(activity.getRunDate())));
				  
				   
				  
				  					
	}
	
	@Test
	public void testDeleteActivityWithCorrectId() throws Exception {
		
		setUp();
		
		mockMvc.perform(delete("/api/activity/{id}",1)
				   .header("Authorization", token)
				   .contentType(MediaType.APPLICATION_JSON_VALUE))
				   .andExpect(status().isOk());
				  			  
				  					
	}
	
	@Test
	public void testDeleteActivityWithIncorrectId() throws Exception {
		
		setUp();
		
		mockMvc.perform(delete("/api/activity/{id}",100000)
				   .header("Authorization", token)
				   .contentType(MediaType.APPLICATION_JSON_VALUE))
				   .andExpect(status().isNotFound());
				  			  
				  					
	}

}
