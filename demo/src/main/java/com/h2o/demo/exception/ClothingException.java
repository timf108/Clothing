package com.h2o.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClothingException extends RuntimeException{
	public ClothingException() {
        super();
    }

    public ClothingException(String message) {
        super(message);
    }

    public ClothingException(String message, Throwable cause) {
        super(message, cause);
    }
}
