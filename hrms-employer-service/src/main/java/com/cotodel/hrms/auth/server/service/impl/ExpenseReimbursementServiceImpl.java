package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseReimbursementDao;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementRequest;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
import com.cotodel.hrms.auth.server.service.ExpenseReimbursementService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class ExpenseReimbursementServiceImpl implements ExpenseReimbursementService{

	@Autowired
	ExpenseReimbursementDao  expenseReimbursementDao;

	@Override
	public ExpenseReimbursementRequest saveExpenseReimbursementFileUpload(ExpenseReimbursementRequest request) {
		String response="";
		ExpenseReimbursementEntity expenseReimbursementEntity=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			expenseReimbursementEntity=new ExpenseReimbursementEntity();
			CopyUtility.copyProperties(request,expenseReimbursementEntity);			
			expenseReimbursementEntity.setStatus(1l);
			Date date = new Date();
			LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			expenseReimbursementEntity.setCreated_date(localDate);
			//
//			if(request.getFile()!=null) {
//				String fileName = request.getFile().getOriginalFilename();
//				expenseReimbursementEntity.setFileName(fileName);
//				expenseReimbursementEntity.setFile(request.getFile().getBytes());
//				expenseReimbursementEntity.setFileType(request.getFile().getContentType());
//			}			 
			//
			expenseReimbursementEntity=expenseReimbursementDao.saveDetails(expenseReimbursementEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return request;
	}

	@Override
	public ExpenseReimbursementEntity getExpenseReimbursementFileDownload(Long id) {
		
		ExpenseReimbursementEntity expenseReimbursementEntity=new ExpenseReimbursementEntity();
		String response="";
		try {
			expenseReimbursementEntity=expenseReimbursementDao.getExpenseDetails(id);
			
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			//request.setResponse(response);
		}
		return expenseReimbursementEntity;
	}


	
	

	
}
