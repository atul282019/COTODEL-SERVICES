package com.cotodel.hrms.auth.server.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CheckSumGeneratorUtil {

	public static String generateCheckSumKey(String stcheckSumdata, String stShaKey) {
		String chkSumKey = null;
		String stCheckType = "SHA-512";
		CheckSumGeneratorUtil hmac = new CheckSumGeneratorUtil();
		chkSumKey = hmac.Sha(stcheckSumdata, stShaKey, stCheckType);
		
		return chkSumKey;

	}

	public String Sha(String checkSumdata, String key, String CheckType) {
		Mac sha512_HMAC = null;
		String result = null;
		String HMAC_SHA = null;
		try {
			byte[] byteKey = key.getBytes("UTF-8");
			if (CheckType.equals("SHA-512")) {
				HMAC_SHA = "HmacSHA512";
			}
			if (CheckType.equals("SHA-256")) {
				HMAC_SHA = "HmacSHA256";
			}
			sha512_HMAC = Mac.getInstance(HMAC_SHA);
			SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA);
			sha512_HMAC.init(keySpec);
			byte[] mac_data = sha512_HMAC.doFinal(checkSumdata.getBytes("UTF-8"));
			// result = Base64.encode(mac_data);
			result = bytesToHex(mac_data);
			System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String getVoucherCreationCheckSum(String corpmobno,String corpupiID,String corpaccNo,String stShaKey) {
		String value="corpmobno-"+corpmobno+"|"+"corpupiID-"+corpupiID+"|"+"corpaccNo-"+corpaccNo;
		//return generateCheckSumKey("corpmobno-917639400793|corpupiID-testbradco@indianbk|corpaccNo-615021536","73127505498180881483015890950210");
		return generateCheckSumKey(value,stShaKey);
	}
	public static String getVoucherRevokeCheckSum(String umn,String orgTxnAmount,String stShaKey) {
		String value="umn-"+umn+"|"+"orgTxnAmount-"+orgTxnAmount;
		//generateCheckSumKey("umn-7c6a860387c84cc18c65fb40d9c2ce92@indianbk|orgTxnAmount-10.00","73127505498180881483015890950210");
		return generateCheckSumKey(value,stShaKey);
	}
	public static String getVoucherInquiryCheckSum(String umn,String orgTxnAmount,String stShaKey) {
		String value="umn-"+umn+"|"+"orgTxnAmount-"+orgTxnAmount;
		//return generateCheckSumKey("umn-7c6a860387c84cc18c65fb40d9c2ce92@indianbk|orgTxnAmount-10.00","73127505498180881483015890950210");
		return generateCheckSumKey(value,stShaKey);
	}
	public static void main(String[] args) {

		//for voucher creation
		System.out.println("voucher creation");
		generateCheckSumKey("corpmobno-917639400793|corpupiID-testbradco@indianbk|corpaccNo-615021536","73127505498180881483015890950210");



//		//		for voucher revoke
		System.out.println("------------");
		System.out.println("revoke");
		generateCheckSumKey("umn-7c6a860387c84cc18c65fb40d9c2ce92@indianbk|orgTxnAmount-10.00","73127505498180881483015890950210");
		System.out.println("------------");

//		for voucher inquiry
//		//1. success
		System.out.println("inquiry");
		generateCheckSumKey("umn-7c6a860387c84cc18c65fb40d9c2ce92@indianbk|orgTxnAmount-10.00","73127505498180881483015890950210");
		//2. failure
//		generateCheckSumKey("umn-743053b9e2a7485881d2ad942e59e7a2@indianbk|orgTxnAmount-1.00","73127505498180881483015890950210");
		//3. failure
//		generateCheckSumKey("umn-743053b9e2a7485881d2ad942e59e7a3@indianbk|orgTxnAmount-1.00","73127505498180881483015890950210");

		
	}
}
