package com.cotodel.hrms.auth.server.util;

public interface CommonUtils {


	public static String existOrgid = "/userServices/Api/get/orgExist";
	public static String sendVoucherCreate = "/erupitesting/Api/callapi/vouchercreation";
	public static String sendVoucherSms = "/erupitesting/Api/callapi/voucherSms";
	public static String sendVoucherStatus = "/erupitesting/Api/callapi/voucherstatus";
//	public static String sendVoucherCreate = "/Api/callapi/vouchercreation";
//	public static String sendVoucherSms = "/Api/callapi/voucherSms";
//	public static String sendVoucherStatus = "/Api/callapi/voucherstatus";
	public static String updateUser = "/userServices/Api/update/updateUsers";
	public static String saveUsersBulk = "/userServices/Api/add/saveUsersBulk";
	public static String saveUsersWithOutMailNew = "/userServices/Api/add/saveUsersWithOutMailNew";
	public static String saveUsersWithOutMail = "/userServices/Api/add/saveUsersWithOutMail";
	public static String userDetailsWithMobile = "/userServices/Api/get/userDetailsWithMobile";
	public static String getToken = "/tokenService/Api/get/access-token";
	public static String verifyToken = "/tokenService/Api/verify/access-token";
	public static String sendIndianVoucherCreate = "/erupitesting/Api/call/indianVoucherCreation";
	public static String sendIndianVoucherRevoke = "/erupitesting/Api/call/indianVoucherRevoke";
	public static String sendIndianVoucherInquary = "/erupitesting/Api/call/indianVoucherInquiry";
	public static String userUserStatus = "/userServices/Api/update/updateUsers";
	public static String getVehicleDetails = "/gstPanService/Api/get/checkVehicleNo";
	//http://13.234.119.146:9084/gstPanService/Api/get/rcapi
}
