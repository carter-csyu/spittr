package com.devchun.spittr.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Duplicated Spitter.")
public class DuplicatedSpitterException extends RuntimeException {
	private static final long serialVersionUID = 2015373323171117512L;

	public DuplicatedSpitterException() {
	}
}
