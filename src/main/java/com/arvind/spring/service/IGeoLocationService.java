package com.arvind.spring.service;

import com.arvind.spring.exception.GeoLocationException;
import com.arvind.spring.model.Address;

public interface IGeoLocationService {
	Address getAddress(String latitude, String longitude) throws GeoLocationException;
}
