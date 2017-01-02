package com.arvind.spring.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class GeoLocation {
	@NotNull
	@Pattern(message = "Latitude must me a number", regexp = "^-?[0-9]\\d*(\\.\\d+)?$")
	public String latitude;
	@NotNull
	@Pattern(message = "Longitude must me a number", regexp = "^-?[0-9]\\d*(\\.\\d+)?$")
	public String longitude;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}