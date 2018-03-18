package com.vanhack.skipthedishes.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 28505586241889807L;

	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message) {
		super(message);
	}
	
}
