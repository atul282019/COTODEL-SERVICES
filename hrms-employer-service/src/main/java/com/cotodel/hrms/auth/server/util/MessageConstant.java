package com.cotodel.hrms.auth.server.util;

import com.google.gson.Gson;

public class MessageConstant {
	

	public static final String RESPONSE_SUCCESS = "SUCCESS";
	public static final String RESPONSE_FAILED = "FAILURE";	
	public static final Integer ONE = 1;
	public static final String OTP_SENT = "Otp Sent Successfully.";
	public static final String OTP_FAILED = "Otp Failed.";
	public static final String PROFILE_SUCCESS = "Data Saved Successfully.";
	public static final String PROFILE_FAILED = "Data Not Saved Successfully.";
	public static final boolean TRUE = true;
	public static final boolean FALSE = false;
	public static final String DATA_NOT_FOUND = "No Data Found .";
	public static final String DATA_FOUND = "Data Found .";
	public static final Gson gson = new Gson();
	public static final String PROFILE_DELETE = "Data Delete Successfully.";
	public static final String PROFILE_DELETE_FAILED = "Data Not Delete Successfully.";
	public static final String ACC_MIS_MATCH = "Account no mismatch.";
	public static final String ORG_ONBOARDING = "Organization is not onboarded.";
	public static final String WORANG_WFID = "Worang work flow id.";
	public static final String DUP_ACC = "Account already exist.";
	public static final String DUP_BANK_CODE = "Bank already exist.";
	public static final String DUP_VR_CODE = "Voucher code already exist.";
}
