package com.cotodel.hrms.auth.server.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;

@Service
public class TemplateService {

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	
	public String getTemplateSms(String templateId) {
		
		switch (templateId) {
	// otp for add new member
		case "1007163593301170578":
			return applicationConstantConfig.BIS_TEMPLATE_OTP_1007163593301170578;
	// otp for download card		
		case "1007163593280345232":
			return applicationConstantConfig.BIS_TEMPLATE_OTP_1007163593280345232;
	// otp for access bis login	
		case "1007163593275058061":
			return applicationConstantConfig.BIS_TEMPLATE_OTP_1007163593275058061;
		// Kyc complete sms 	
		case "1007163593307833597":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007163593307833597;
		// SMS for add new member
		case "1007163593317209694":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007163593317209694;
		case "1007164684043366664":
			return applicationConstantConfig.BIS_TEMPLATE_OTP_1007164684043366664;
		case "1007166013592993070":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007166013592993070;
		case "1007166013575944759":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007166013575944759;
		case "1007166013579066985":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007166013579066985;
		case "1007166255090158204":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007166255090158204;	
		case "1007164982263345893":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007164982263345893;	
		case "1007168051684626090":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007168051684626090;	
		case "1007168171513942130":
			return applicationConstantConfig.BIS_TEMPLATE_SMS_1007168171513942130;	
		
			
		default:
			break;
		}
		return null;
	
	}
	
	
}
