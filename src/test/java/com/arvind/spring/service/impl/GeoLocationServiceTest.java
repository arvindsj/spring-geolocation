package com.arvind.spring.service.impl;

import static com.arvind.spring.util.GeoConstants.EMPTY_STRING;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.arvind.spring.exception.GeoLocationException;
import com.arvind.spring.model.Address;
/**
 * 
 * Geolocations				Address
 * 33.969601,-84.100033		2651 Satellite Blvd, Duluth, GA 30096, USA
 * 33.762742,-84.392664		121 Baker St NW, Atlanta, GA 30313
 * 48.858887,2.294486		5 Avenue Anatole France, 75007 Paris, France
 * 37.791035,-122.393391	182 Howard Street # 8 San Francisco, CA 94105
 * 40.725332,-74.005495		246 Spring St, New York, NY 10013
 * 38.887080,-77.009066		H-232 The Capitol Washington, DC 20515
 * 41.880033,-87.639074		120 S. Riverside Chicago, IL 60606
 * 34.001959,-84.082586		1611 Satellite Blvd Duluth, GA 30097
 * 34.191659,-83.898854		4400 Falcon Parkway. Flowery Branch, GA 30542
 * 42.256162,-87.895914		1920 Football Drive, Lake Forest, IL 60045
 * 39.578826,-104.830782	13655 Broncos Parkway. Englewood, CO 80112
 *
 */
public class GeoLocationServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationServiceTest.class);
	@InjectMocks 
	GeoLocationService geoService;
	@Mock
	Properties properties;
	@Spy
	private RestTemplate template;

	private String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=33.969601,-84.100033&key=KEY";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.doReturn(EMPTY_STRING).when(properties).getProperty(Mockito.anyString());
	}

	@Test
	public void testValidGeoCoordinates() throws GeoLocationException {
		when(geoService.getURI(EMPTY_STRING,EMPTY_STRING)).thenReturn(url);
		Address address = geoService.getAddress(EMPTY_STRING, EMPTY_STRING);
		LOGGER.info(address.getStreetAddress());
		Assert.assertEquals("2651 Satellite Blvd, Duluth, GA 30096, USA", address.getStreetAddress());
	}
	
	@Test(expected = GeoLocationException.class)  
	public void testInValidGeoCoordinates() throws GeoLocationException {
		when(geoService.getURI(EMPTY_STRING,EMPTY_STRING)).thenReturn("https://maps.googleapis.com/maps/api/geocode/json?latlng=33.969601,-84.100033&key=KEY");
		Address address = geoService.getAddress(EMPTY_STRING, EMPTY_STRING);
		LOGGER.info(address.getStreetAddress());
	}
	
}
