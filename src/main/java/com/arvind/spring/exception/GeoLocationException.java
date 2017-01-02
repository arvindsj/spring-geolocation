package com.arvind.spring.exception;

import java.io.IOException;

public class GeoLocationException extends IOException {
	private static final long serialVersionUID = -8685128635481502745L;

	public GeoLocationException() {
	}

	public GeoLocationException(String message) {
		super(message);
	}
}
