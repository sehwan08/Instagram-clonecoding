package com.cos.photogramstart.handler.ex;


public class CustomException extends RuntimeException {

	//객체 구분용 - 중요하지 않음
	private static final long serialVersionUID = 1L;
	
	public CustomException(String message) {
		super(message);
	}
}
