package com.arvind.spring.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class GeoLocationResourceExceptionHandler implements ResponseErrorHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationResourceExceptionHandler.class);

	@Override
	public boolean hasError(ClientHttpResponse paramClientHttpResponse)
			throws IOException {
		if (paramClientHttpResponse.getStatusCode() != HttpStatus.OK) {
			// Log some common errors
			if (paramClientHttpResponse.getStatusCode() == HttpStatus.FORBIDDEN) {
				LOGGER.error("Call returned a error 403 FORBIDDEN resposne ");
			}
			if (paramClientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				LOGGER.error("Call returned a error 404 NOT_FOUND");
			}
			if(paramClientHttpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				LOGGER.error("Call returned a error 401 UNAUTHORIZED");
			}
			LOGGER.error("Status code:: " + paramClientHttpResponse.getStatusCode());
			LOGGER.error("Status Text::" + paramClientHttpResponse.getStatusText());
			
			return true;
		}
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		String error = String.format("Response error: {%s}-{%s} ", response.getStatusCode(), response.getStatusText());
		LOGGER.error(error);
		throw new GeoLocationException(error);
		
	}
}