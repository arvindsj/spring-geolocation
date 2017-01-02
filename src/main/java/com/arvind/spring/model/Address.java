package com.arvind.spring.model;

import java.util.List;

public class Address {
	
	public Address(String streetAddress) {
		super();
		this.streetAddress = streetAddress;
	}

	public Address() {
		super();
	}

	private String streetAddress;
	private List<String> errors;

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}