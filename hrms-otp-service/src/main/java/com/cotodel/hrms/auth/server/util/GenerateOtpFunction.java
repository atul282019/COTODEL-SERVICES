package com.cotodel.hrms.auth.server.util;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateOtpFunction {

	private static final Logger logger = LoggerFactory.getLogger(GenerateOtpFunction.class);

	public static String generateOTP() {
		String otp=null;
		try {

			Random rnd = new Random();
			int n = 100000 + rnd.nextInt(900000);
			otp = String.valueOf(n);
		}catch (Exception e) {
			logger.info(e.getMessage());// TODO: handle exception
		}
		return otp;
	}

}
