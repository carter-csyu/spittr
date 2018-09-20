package com.devchun.spittr.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.devchun.spittr.Spittle;
import com.devchun.spittr.data.SpittleRepository;

public class SpittleControllerTest {

	@Test
	public void testSpittle() throws Exception {
		Spittle expectedSpittle = new Spittle((long)100, "Spittle" + 100, new Date(), null, null);
		SpittleRepository mockRepository = mock(SpittleRepository.class);
		when(mockRepository.findOne(100))
		.thenReturn(expectedSpittle);
		
		SpittleController controller = new SpittleController(mockRepository);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/spittles/100"))
			.andExpect(view().name("spittle"))
			.andExpect(model().attributeExists("spittle"))
			.andExpect(model().attribute("spittle", expectedSpittle));
	}
}
