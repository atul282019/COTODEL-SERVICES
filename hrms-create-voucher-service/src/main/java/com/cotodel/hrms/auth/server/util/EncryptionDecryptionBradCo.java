package com.cotodel.hrms.auth.server.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryptionBradCo {

	public static String encryptMaster(String data, String key) {
		String encryptedValue = "";
		String initVector = "9568463295684632";
		int GCM_TAG_LENGTH = 16;
		try {

			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, initVector.getBytes());
			SecretKeySpec skp = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skp, gcmParameterSpec);
			byte[] encrypted = cipher.doFinal(data.getBytes());
			encryptedValue = Base64.getEncoder().encodeToString(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedValue;
	}

	public static String decryptMaster(String data, String key) {

		String decryptedData = "";
		String initVector = "9568463295684632";
		int GCM_TAG_LENGTH = 16;
		try {

			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, initVector.getBytes());
			SecretKeySpec skp = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skp, gcmParameterSpec);
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
			decryptedData = new String(decrypted);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedData;
	}
	public static String voucherCreate(String data, String key) {
		
		String bradcoStr1 = encryptMaster(data, key);
		return bradcoStr1;
	}
	public static String voucherInquiry(String data, String key) {
		
		String bradcoStr1 = encryptMaster(data, key);
		return bradcoStr1;
	}
	public static String voucherRevoke(String data, String key) {
		
		String bradcoStr1 = encryptMaster(data, key);
		return bradcoStr1;
	}

	public static void main(String[] args) {
		String enDeKey = "01428169FE856B02EA3998A4E0C92D84";
//		Scanner sc = new Scanner(System.in);
//		System.out.print("Beneficiary Mobile number: \t");
//		String beneMobile = sc.next();
//		System.out.print("StartDate : \t");
//		String startDate = sc.next();
//		System.out.print("end Date: \t");
//		String endDate = sc.next();
		//bradco
				String bradcoStr1 = encryptMaster("{\"corpid\":\"\",\"corpmobno\":\"917639400793\",\"corpupiID\":\"testbradco@indianbk\",\"corpaccNo\":\"615021536\",\"corpaccType\":\"SB\",\"corpifsc\":\"IDIB000S269\",\"corpname\":\"BRADCO GANGA KALYANA SCHEME\",\"benemobNo\":\"919421991523\",\"benename\":\"Navnath\",\"benemailId\":\"navnath.satpute@kiya.ai\",\"beneIdName\":\"DL\",\"beneIdno\":\"HR0443438448\",\"purposecode\":\"B2\",\"revocable\":\"N\",\"amount\":\"10.00\",\"validitystartdate\":\"11032025\",\"validityenddate\":\"30122025\",\"initiationMode\":\"00\",\"merchantid\":\"157930\",\"payeeVPA\":\"\",\"payermcc\":\"1711\",\"payeemcc\":\"1711\",\"recurrencePattern\":\"ONETIME\",\"checksum\":\"E31A8C4D857FCD709095E3BFE66EE4A12631421A04EA6465BCE48A593C441D1C9CF2656138E472E06E30F76E5CF2279CF89798EC963CB06ECCB28EA0EF50ECBC\"}", enDeKey);
			
			
				System.out.println("{\"data\": \"" + bradcoStr1 + "\"}");
				System.out.println("Cloud :: ");
				System.out.println();


		//voucher inquiry
		//1. created
		String inquiryStr = "{\r\n" +
				"\"action\": \"inquiry\", \"subaction\": \"inquiry\", "
				+ "\"entityID\": \"INB\", \"inputparam\": {\r\n" +
				"\"txnrefID\": \"INBf5597fc4ed434cf7871dcaf9894bba59\",\r\n" +
				"\"umn\": \"7c6a860387c84cc18c65fb40d9c2ce92@indianbk\", "
				+ "\"txndatetime\": \"2023-03-21 17:49:46.226\",\r\n" +
				"\"orgTxnAmount\": \"10.00\",\r\n" +
				"\"checkSum\": \"BEF00EAE35BB7D433A3157B28F9D95B509E8FBDB5F16D93897AC7E7181A7A8E54418D7DBDABF4E97B6BAE8C47EACF503379E0214CF3AD2DF71FE23E73601C62A\"\r\n" +
				"}\r\n" +
				"}\r\n" +
				"";
		System.out.println("--------enquiry--------");
		System.out.println("{\"data\": \"" + encryptMaster(inquiryStr, enDeKey) + "\"}");




		System.out.println("revoke");
		String revokeStr = "{\r\n" +
				"\"action\": \"revoke\", \"subaction\": \"revoke\", "
				+ "\"entityId\": \"INB\", "
				+ "\"inputparam\": {\r\n" +
				"\"txnrefID\": \"INBafbe1c3c518c4a1f947d2eff07c74e17\",\r\n" +
				"\"umn\": \"7c6a860387c84cc18c65fb40d9c2ce92@indianbk\", \r\n" +
				"\"orgTxnId\": \"INB37a3e1d389f64a5ba145dc69e7406aad\",\r\n" +
				"\"orgTxnDate\": \"2024-05-28 15:23:29.399\",\r\n" +
				"\"orgTxnAmount\": \"10.00\",\r\n" +
				"\"txndatetime\": \"2024-05-28 15:23:29.399\",\r\n" +
				"\"checkSum\": \"BEF00EAE35BB7D433A3157B28F9D95B509E8FBDB5F16D93897AC7E7181A7A8E54418D7DBDABF4E97B6BAE8C47EACF503379E0214CF3AD2DF71FE23E73601C62A\"\r\n" +
				"}\r\n" +
				"}\r\n" +
				"";

		String voucherJson = encryptMaster(revokeStr, enDeKey);
		System.out.println("{\"data\": \"" + voucherJson + "\"}");
		System.out.println();

		
	}
}