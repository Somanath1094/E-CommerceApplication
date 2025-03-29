package com.ecommerce.app.exception;

public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}

