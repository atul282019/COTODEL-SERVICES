package com.cotodel.hrms.auth.server.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.util.UUID;

public class TransactionManager {

	public static String getTransactionId() {

		return "NHA:"+UUID.randomUUID().toString();
	}

	public static String getKycTransactionId() {

		return "UKC:"+UUID.randomUUID().toString();
	}
	
	
	public static String getFaceIdTransactionId() {

		return "NHAFACEID:"+UUID.randomUUID().toString();
	}


	private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
	public static String otpTxnId(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	
	
	private static final String NUMERIC_STRING = "123456789";
	public static String TxnId(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static String getCode() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String strDate = sdf.format(cal.getTime());
		System.out.println("Current date in String Format: " + strDate);

		String dateStr=	strDate.replaceAll("/", "").replaceAll(" ", "").replaceAll(":", "");
		
	     return "18000"+dateStr+TransactionManager.TxnId(5);
	}
	
	public static String getCurrentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(cal.getTime());
		return strDate;
	}
	
	public static void main(String[] args) {
		
		System.out.println(TxnId(10));
		
	}
	
}
