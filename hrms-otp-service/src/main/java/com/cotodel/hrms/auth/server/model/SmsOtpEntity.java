package com.cotodel.hrms.auth.server.model;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sms_otp")
@Access(value=AccessType.FIELD)
@SequenceGenerator(name="sms_otp_id_seq" , sequenceName="sms_otp_id_seq", allocationSize=1)
public class SmsOtpEntity implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2884824138496445115L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sms_otp_id_seq")
	private Integer smsid;
	
	
	private Integer  otp;
    private String mobile ;
    private String txn ;
    private Date smsdate;
    private String ip;
    private boolean status ;
    private String res;
    
    
    
    
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public Integer getSmsid() {
		return smsid;
	}
	public void setSmsid(Integer smsid) {
		this.smsid = smsid;
	}
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTxn() {
		return txn;
	}
	public void setTxn(String txn) {
		this.txn = txn;
	}
	public Date getSmsdate() {
		return smsdate;
	}
	public void setSmsdate(Date smsdate) {
		this.smsdate = smsdate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
    
    

}
