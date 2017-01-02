package com.arvind.spring.model;

import java.sql.Timestamp;

public class GeoLocationsHistory extends GeoLocation {
	Address address;
	Timestamp searchedDateTimestamp;

	public Timestamp getSearchedDateTimestamp() {
		return searchedDateTimestamp;
	}

	public void setSearchedDateTimestamp(Timestamp searchedDateTimestamp) {
		this.searchedDateTimestamp = searchedDateTimestamp;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}