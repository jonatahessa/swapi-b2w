package com.jonata.swapi.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlanetInvalidAttribute extends Exception {
	private static final long serialVersionUID = 1L;

	public PlanetInvalidAttribute(String msg) {
		super(msg);
	}
}