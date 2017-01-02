package com.arvind.spring.service.impl;

import static com.arvind.spring.util.GeoConstants.ADDRESS;
import static com.arvind.spring.util.GeoConstants.ERROR_MESSAGE;
import static com.arvind.spring.util.GeoConstants.ROOT_NODE;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arvind.spring.exception.GeoLocationException;
import com.arvind.spring.exception.GeoLocationResourceExceptionHandler;
import com.arvind.spring.model.Address;
import com.arvind.spring.service.IGeoLocationService;

@Service
public class GeoLocationService implements IGeoLocationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationService.class);
	@Autowired
	private java.util.Properties properties;
	RestTemplate restTemplate;
	private static final String MAP_URI = "map.uri";

	@PostConstruct
	public RestTemplate createRestTemplate() {
		restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new GeoLocationResourceExceptionHandler());
		return restTemplate;
	}

	@Override
	public Address getAddress(String latitude, String longitude) throws GeoLocationException {
		String jsonResult;
		ResponseEntity<String> responseEntity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		responseEntity = restTemplate.exchange(getURI(latitude, longitude), HttpMethod.GET, entity, String.class);
		// Invalid Key, but error code 200!
		if (responseEntity.getBody().contains(ERROR_MESSAGE)) {
			LOGGER.info(responseEntity.getBody());
			throw new GeoLocationException("INVALID URI");
		}
		jsonResult = responseEntity.getBody();
		LOGGER.info(jsonResult);
		return getAddress(jsonResult);
	}

	/**
	 * 
	 * @param addressJson
	 * @return address
	 * @Description: This method parses the JSON string returned from the REST
	 *               API. Chose org.json library for easy parsing.
	 */
	private Address getAddress(final String addressJson) {
		Address address = new Address();
		JSONObject stAdd = null;
		JSONObject jsonObj = new JSONObject(addressJson);
		JSONArray results = (JSONArray) jsonObj.get(ROOT_NODE);
		if(results!=null && results.length() > 0) {
		// [0]'th element is the most relevant address
		stAdd = results.getJSONObject(0);
		address.setStreetAddress(stAdd.getString(ADDRESS));
		}
		return address;
	}

	public String getURI(final String latitude, final String longitude) {
		String URI = String.format(properties.getProperty(MAP_URI), latitude, longitude);
		LOGGER.info("URI::" + URI);
		return URI;
	}

}
