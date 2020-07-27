package com.jonata.swapi.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlanetDuplicatedInDatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PlanetDuplicatedInDatabaseException(String msg) {
		super(msg);
	}
}