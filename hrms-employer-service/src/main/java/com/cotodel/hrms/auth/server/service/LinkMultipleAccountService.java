package com.cotodel.hrms.auth.server.service;

import java.util.List;

import com.cotodel.hrms.auth.server.dto.LinkMultipleAccountRequest;
import com.cotodel.hrms.auth.server.dto.LinkMultipleAccountUpdate;

public interface LinkMultipleAccountService {
	
	public LinkMultipleAccountRequest  saveMultipleAccountRequest(LinkMultipleAccountRequest request);
	public LinkMultipleAccountUpdate  saveMultipleAccountOrUpdateDr(LinkMultipleAccountUpdate request);
	public LinkMultipleAccountUpdate  saveMultipleAccountOrUpdateCr(LinkMultipleAccountUpdate request);
	public List<LinkMultipleAccountRequest>  getMultipleAccountList(LinkMultipleAccountRequest request);
	public LinkMultipleAccountRequest  updateMultipleAccount(LinkMultipleAccountRequest request);
	public String  getMultipleAccountBalance(LinkMultipleAccountRequest request);
}
