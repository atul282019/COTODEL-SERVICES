package com.cotodel.hrms.auth.server.util;

public interface CommonUtils {


	public static String existOrgid = "/userServices/Api/get/orgExist";
	public static String sendVoucherCreate = "/erupitesting/Api/callapi/vouchercreation";
	public static String sendVoucherSms = "/erupitesting/Api/callapi/voucherSms";
	public static String sendVoucherStatus = "/erupitesting/Api/callapi/voucherstatus";
	public static String updateUser = "/userServices/Api/update/updateUsers";
	public static String saveUsersBulk = "/userServices/Api/add/saveUsersBulk";
	public static String saveUsersWithOutMailNew = "/userServices/Api/add/saveUsersWithOutMailNew";
	public static String saveUsersWithOutMail = "/userServices/Api/add/saveUsersWithOutMail";
	public static String getToken = "/tokenService/Api/get/access-token";
	public static String verifyToken = "/tokenService/Api/verify/access-token";
	//auth.token.get.url=http://13.234.119.146:8082/tokenService/Api/get/access-token
	//auth.token.verify.url=http://13.234.119.146:8082/tokenService/Api/verify/access-token
	//user.service.add.url=http://13.234.119.146:8088/userServices/Api/add/saveUsersWithOutMail
	//user.service.add.new.url=http://13.234.119.146:8088/userServices/Api/add/saveUsersWithOutMailNew
	//http://13.234.119.146:8088/userServices/Api/add/saveUsersBulk
	//user.service.update.bulk.url=http://13.234.119.146:8088/userServices/Api/update/updateUsers
}
