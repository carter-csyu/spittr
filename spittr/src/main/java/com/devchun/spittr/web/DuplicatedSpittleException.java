package com.devchun.spittr.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Duplicated Spittle.")
public class DuplicatedSpittleException extends RuntimeException {
	private static final long serialVersionUID = -5578521569623420080L;
	public DuplicatedSpittleException() {
		super("Duplicated Spittle.");
	}
}
