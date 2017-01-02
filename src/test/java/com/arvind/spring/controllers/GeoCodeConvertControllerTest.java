package com.arvind.spring.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;

import com.arvind.spring.model.GeoLocation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring.xml" })
public class GeoCodeConvertControllerTest {
	@Autowired
	ApplicationContext context;
	private MockMvc mockMvc;
	@Mock
	GeoLocation geoLocation;
	@Mock
	BindingResult bindingResult;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(bindingResult.hasErrors()).thenReturn(false);
		mockMvc = standaloneSetup(new GeoCodeConvertController()).build();
		when(geoLocation.getLatitude()).thenReturn("42.256162");
		when(geoLocation.getLatitude()).thenReturn("-87.895914");

	}

	@Test
	public void testAllGeoLocations() throws Exception {
		final ResultActions perform = mockMvc.perform(get("/allgeolocations"));
		perform.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(""));
	}

	@Test
	public void testGetGeoLocation() throws Exception {
		final ResultActions perform = mockMvc.perform(get("/geolocation"));
		perform.andExpect(status().isOk()).andExpect(content().string(""));
	}

	@Test
	public void testPostGeoLocation() throws Exception {
		final ResultActions perform = mockMvc.perform(post("/geolocation"));
		perform.andExpect(status().isOk())
				.andExpect(
						content()
								.string("{\"errors\":[\"may not be null\",\"may not be null\"]}"));
	}
}
