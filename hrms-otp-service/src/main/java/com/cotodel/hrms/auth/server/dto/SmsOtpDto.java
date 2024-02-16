package com.cotodel.hrms.auth.server.dto;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.io.Serializable;

public class SmsOtpDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7571570726818610434L;
	private String mobile;
	private String otp;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	

}
