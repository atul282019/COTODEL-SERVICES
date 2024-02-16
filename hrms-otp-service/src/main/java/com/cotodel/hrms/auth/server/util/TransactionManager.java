package com.cotodel.hrms.auth.server.util;
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


	private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
	public static String otpTxnId(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	private static final String ALPHA_NUMERIC_STRING1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String otpTxnId1(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString().toUpperCase();
	}

	public static void main(String[] args) {
		
		System.out.println(otpTxnId1(9));
		
	}
	
}
