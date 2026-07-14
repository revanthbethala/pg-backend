package com.pg.exception;

public class RoomUnderMaintainanceException extends RuntimeException {

	public RoomUnderMaintainanceException(String message) {
		super(message);
	}
	public RoomUnderMaintainanceException() {
		super("Room Under Maintainance");
	}

}
