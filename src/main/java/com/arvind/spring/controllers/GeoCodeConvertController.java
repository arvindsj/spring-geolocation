package com.arvind.spring.controllers;

import static com.arvind.spring.util.GeoConstants.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arvind.spring.exception.GeoLocationException;
import com.arvind.spring.model.Address;
import com.arvind.spring.model.GeoLocation;
import com.arvind.spring.model.GeoLocationsHistory;
import com.arvind.spring.service.IGeoLocationService;
import com.arvind.spring.util.LocalCache;
import com.google.gson.Gson;

@Controller
public class GeoCodeConvertController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoCodeConvertController.class);
	@Autowired
	private IGeoLocationService geoLocationService;
	
	@Autowired
	private java.util.Properties properties;
	
	private LocalCache<String, GeoLocationsHistory> cache = new LocalCache<>(MAX_CACHE_SIZE);
	
	private Gson gson = new Gson();

	@RequestMapping(value = "/geolocation", method = RequestMethod.GET)
	public String getGeoLocation(Model model) {
		LOGGER.info("Start::GET::getGeoLocation()");
		model.addAttribute("geoLocation", new GeoLocation());
		model.addAttribute("localcache", getSearchedValues());
		LOGGER.info("End::GET::getGeoLocation()");
		return "reversegeo";
	}
	/**
	 * @param geoLocation
	 * @param bindingResult
	 * @return String
	 * @throws GeoLocationException 
	 * @Description: This method gets the Latitude and Longitude from UI and accesses the MapQuest REST API to get
	 * the address.
	 */
	@RequestMapping(value = "/geolocation", method = RequestMethod.POST)
	@ResponseBody
	public String getAddressByGeoLocations(@Valid final GeoLocation geoLocation, final BindingResult bindingResult) throws GeoLocationException {
		LOGGER.info("Start::POST::getAddressByGeoLocations()");
		Address address = new Address();
		// Handle Error
		if (bindingResult.hasErrors()) {
			final List<String> errorsList = new ArrayList<String>(2);
			bindingResult.getAllErrors().forEach(objectError -> errorsList.add(objectError.getDefaultMessage()));
			address.setErrors(errorsList);
			return gson.toJson(address).toString();
		}
		address = findIfGeoLocationIsInCache(geoLocation.getLatitude(), geoLocation.getLongitude());
		if(address == null) {
			address = geoLocationService.getAddress(geoLocation.getLatitude(), geoLocation.getLongitude());
			if(address == null || address.getStreetAddress() == null) {
				return EMPTY_STRING;
			}
		}
		populateCache(geoLocation.getLatitude(), geoLocation.getLongitude(), address);
		return returnJSON();
	}
	/**
	 * @return GeoLocationHistory string
	 * @Description: This method reads the cache (User search) and exposes the search result as JSON.
	 */
	@RequestMapping(value = "/allgeolocations", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getSearchedValues() {
		return returnJSON();
	}
	
	private String returnJSON() {
		if (cache.isEmpty()) {
			return EMPTY_STRING;
		}
		List<GeoLocationsHistory> look = new ArrayList<>(MAX_CACHE_SIZE);
		cache.entrySet().forEach(entry -> look.add(entry.getValue()));
		LOGGER.info(gson.toJson(look).toString());
		return gson.toJson(look).toString();	
	}
	
	private void populateCache(final String latitude, final String longitude, final Address address) {
		if(address != null && address.getStreetAddress() != null && address.getStreetAddress() != EMPTY_STRING) {
			GeoLocationsHistory history = new GeoLocationsHistory();
			history.setLatitude(latitude);
			history.setLongitude(longitude);
			history.setAddress(address);
			history.setSearchedDateTimestamp(new Timestamp(new Date().getTime()));
			cache.put(latitude.concat(longitude), history);
		}
	}
	// Find the address in the cache. If found, return the address from the cache. Otherwise, access the API
	private Address findIfGeoLocationIsInCache(final String latitude, final String longitude) {
		return cache.containsKey(latitude.concat(longitude)) ? cache.get(latitude.concat(longitude)).getAddress() : null;
	}
}