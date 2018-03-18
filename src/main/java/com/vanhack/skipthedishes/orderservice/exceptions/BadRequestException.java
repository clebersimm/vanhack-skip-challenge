package com.vanhack.skipthedishes.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 2556901860696947180L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

}
