package com.cotodel.hrms.auth.server.exception;

public class ApiExceptions  extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String description;
	public ApiExceptions(int code,String msg) {
		super(msg);
		this.code=code;
		this.description=msg;
	}
	public int getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}

	
	
}
