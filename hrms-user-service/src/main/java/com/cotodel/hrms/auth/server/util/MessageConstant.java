package com.cotodel.hrms.auth.server.util;

import com.google.gson.Gson;

public class MessageConstant {
	
	public static final Gson gson = new Gson();
	public static final String RESPONSE_SUCCESS = "SUCCESS";
	public static final String RESPONSE_FAILED = "FAILURE";	
	public static final Integer ONE = 1;
	public static final String OTP_SENT = "Otp Sent Successfully.";
	public static final String OTP_FAILED = "Otp Failed.";
	public static final String USER_DEACTIVE = "You are not authorized to access.";
	public static final String PROFILE_SUCCESS = "Data Saved Successfully.";
	public static final String PROFILE_FAILED = "Data Not Saved Successfully.";
	public static final String USER_EMAIL_ALREADY_VERIFIED = "User Already verified with this email!!";
	public static final String USER_EMAIL_VERIFIED = "User verified with this email!!";
	public static final String USER_EMAIL_NOT_VERIFIED = "User not verified with this email!!";
	public static final String USER_EXIST = "User Already exist with this email or mobile number !!";
	public static final boolean TRUE = true;
	public static final boolean FALSE = false;
	public static final Integer SIGN_UP_ROLE =1;
	public static final Integer USER_ROLE =2;
	public static final Integer REPUTE_ROLE =3;
	public static final Integer STATUS =1;
	public static final Integer ERUPI_ADMIN_ROLE =9;
	public static final String DATA_NOT_FOUND = "No Data Found .";
	public static final String DATA_FOUND = "Data Found .";
	public static final String CONSENT_CREATED_BY = "Either 'consent' or 'createdBy' must be provided.";
	public static final String USER_BULK_EXIST = "User Already exist with this mobile number !!";
	public static final String INVALID_CAPTCHA = "Invalid Captcha";
	public static final String USER_EMAIL = "User Already exist with this Email !!";
	public static final String HOLD = "Hold";
	public static final String REJECTED = "Rejected";
	public static final String SECRET_KEY = "0123456789012345";
	public static final String HASH_ERROR = "Tempered Request";
	public static final String ORG_EXIST ="This company is already registered.";
	public static final String ELIG_NOT_ORG_EXIST ="You are not eligible to register a company.";
	public static final String MOBILENULL ="Mobile should not be null.";
	public static final String NAMENULL ="Name should not be null.";
	public static final String EMAILNULL ="Email should not be null.";
	public static final String MOBILENOTEXIST = "Mobile does not exist.";	
	public static final String ORG_CHECK_EXIST = "User already exist! Please check your Email or Mobile !!";
	public static final String BANKCODE = "ICICI";
}
