package com.dkatalis.park.exception;

public enum ErrorCode {

	PARKING_ALREADY_EXIST("Sorry Parking Already Created, It can not be created again."), 
	PARKING_NOT_EXIST_ERROR("Sorry, Car Parking Does not Exist"),
	INVALID_VALUE("{variable} value is incorrect"),
	INVALID_FILE("Invalid File"),
	PROCESSING_ERROR("Processing Error "),
	INVALID_REQUEST("Invalid Request");
	
	private String message = "";
	
	private ErrorCode(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
