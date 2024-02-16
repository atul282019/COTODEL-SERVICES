package com.cotodel.hrms.auth.server.response;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class OtpServiceResponse {
	
	public static SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX:ss");
	
	public static String otpServiceResponse(String txn,String status,String errorCode, String errDes ) {
		JSONObject sRturnResponse = new JSONObject();
		
		
		sRturnResponse.put("txn", txn);
		sRturnResponse.put("ts", mdyFormat.format(new Date()));
		sRturnResponse.put("status", status);
		sRturnResponse.put("errCode",  errorCode);
		sRturnResponse.put("errDes",  errDes);
		
		
		return sRturnResponse.toString();
	}
	
	
		

}
