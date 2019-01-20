package com.mainsoft.backend;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.mainsoft.backend.entity.Login;
import com.mainsoft.backend.repository.UserRepository;



/**
 * integration tests for authcontroller
 * 
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value= {"/testdb/create-test-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value= {"/testdb/delete-test-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class TestAuthController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testLoginWithCorrectUserNameAndPassword() throws Exception {
		Login login =new Login();
		login.setUsername("valera");
		login.setPassword("valera");
		

		mockMvc.perform(post("/api/auth/login").content(JsonUtil.toJson(login))
			   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.tokenType").value("Bearer"))
			   .andExpect(jsonPath("$.accessToken").isNotEmpty());

			   
	}
	
	@Test
	public void testLoginWithIncorrectUserNameAndPassword() throws Exception {
		Login login =new Login();
		login.setUsername("user228");
		login.setPassword("user");
		

		mockMvc.perform(post("/api/auth/login").content(JsonUtil.toJson(login))
			   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			   .andExpect(status().isBadRequest());
			   		   
	}
	
	
	@Test
	public void testRegisterUser() throws Exception {
		Login login =new Login();
		login.setUsername("pavlik");
		login.setPassword("1234567");
		

		mockMvc.perform(post("/api/auth/register").content(JsonUtil.toJson(login))
			   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			   .andExpect(status().isOk());
		
		assertTrue(userRepository.existsByUsername("pavlik"));
		
	
			   		   
	}
	
	@Test
	public void testRegisterUserWithIncorrectUserNamePassword() throws Exception {
		Login login =new Login();
		login.setUsername("pav");
		login.setPassword("12");
		

		mockMvc.perform(post("/api/auth/register").content(JsonUtil.toJson(login))
			   .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			   .andExpect(status().isBadRequest());
		
			   		   
	}


}
