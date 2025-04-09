package com.cotodel.hrms.auth.server.util;

import com.google.gson.Gson;

public class MessageConstant {
	

	public static final Integer ONE = 1;
	public static final String OTP_SENT = "Otp Sent Successfully.";
	public static final String OTP_FAILED = "Otp Failed.";
	public static final String PROFILE_SUCCESS = "Data Saved Successfully.";
	public static final String PROFILE_FAILED = "Data Not Saved Successfully.";
	public static final String USER_EMAIL_ALREADY_VERIFIED = "User Already verified with this email!!";
	public static final String USER_EMAIL_VERIFIED = "User verified with this email!!";
	public static final String USER_EMAIL_NOT_VERIFIED = "User not verified with this email!!";
	public static final String USER_EXIST = "User Already exist with this email or mobile number !!";
	public static final Integer SIGN_UP_ROLE =1;
	public static final Integer USER_ROLE =2;
	public static final Integer STATUS =1;
	public static final Gson gson = new Gson();
	public static final String RESPONSE_SUCCESS = "SUCCESS";
	public static final String RESPONSE_FAILED = "FAILURE";	
	public static final boolean FALSE = false;
	public static final boolean TRUE = true;
	public static final String SUCCESS = "Get Back Soon!";
	public static final String DATA_NOT_FOUND = "No Data Found .";
	public static final String DATA_FOUND = "Data Found .";
}
