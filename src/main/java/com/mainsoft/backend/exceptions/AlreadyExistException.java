package com.mainsoft.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistException extends NamedException {

	public AlreadyExistException(String message) {
		super(message, AlreadyExistException.class);

	}

}
