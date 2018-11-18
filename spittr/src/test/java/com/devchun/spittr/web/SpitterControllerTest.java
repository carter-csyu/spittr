package com.devchun.spittr.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.devchun.spittr.Spitter;
import com.devchun.spittr.data.SpitterRepository;

public class SpitterControllerTest {

  private static Logger logger = LoggerFactory.getLogger(SpitterControllerTest.class);
  
	@Test
	public void shouldShowRegistration() throws Exception {
		SpitterController controller = new SpitterController();
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/spitter/register"))
			.andExpect(view().name("registerForm"));
	}
	
	@Test
	public void shouldProcessRegistration() throws Exception {
		SpitterRepository mockRepository = mock(SpitterRepository.class);
		Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "test@email.com");
		Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "test@email.com");
		when(mockRepository.save(unsaved)).thenReturn(saved);
		
		SpitterController controller = new SpitterController(mockRepository);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(post("/spitter/register")
				.param("firstName", "Jack")
				.param("lastName", "Bauer")
				.param("username", "jbauer")
				.param("password", "24hours")
				.param("email", "test@email.com"))
			.andExpect(redirectedUrl("/spitter/jbauer"));
	}
	
	@Test
	public void standardPasswordEncoder() {
	  StandardPasswordEncoder encoder = new StandardPasswordEncoder("chundol42");
	  
	  logger.info(encoder.encode("1q2w3e4r!"));
	}
}
