package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseTravelAdvanceDao;
import com.cotodel.hrms.auth.server.dto.ExpenseTravelAdvanceRequest;
import com.cotodel.hrms.auth.server.model.ExpanceTravelAdvanceEntity;
import com.cotodel.hrms.auth.server.service.ExpenseTravelAdvanceService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class ExpenseTravelAdvanceServiceImpl implements ExpenseTravelAdvanceService{

	@Autowired
	ExpenseTravelAdvanceDao  expenseTravelAdvanceDao;

	@Override
	public ExpenseTravelAdvanceRequest saveExpenseTravelAdvenceDetails(ExpenseTravelAdvanceRequest request) {
		String response="";
		ExpanceTravelAdvanceEntity employeeBandEntity=null;
		ExpanceTravelAdvanceEntity employeeBandEntity1=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			employeeBandEntity=new ExpanceTravelAdvanceEntity();
			
			employeeBandEntity=expenseTravelAdvanceDao.findByEmployerId(request.getEmployerId());
			if(employeeBandEntity!=null) {
				employeeBandEntity1=new ExpanceTravelAdvanceEntity();
				CopyUtility.copyProperties(request,employeeBandEntity1);
				employeeBandEntity1.setStatus(1l);
				employeeBandEntity1.setId(employeeBandEntity.getId());
				employeeBandEntity1.setCreated_date(employeeBandEntity.getCreated_date());
				Date date = new Date();
				LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				employeeBandEntity1.setModified_date(localDate);
				employeeBandEntity1=expenseTravelAdvanceDao.saveDetails(employeeBandEntity1);
				response=MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}else {
				employeeBandEntity=new ExpanceTravelAdvanceEntity();
				CopyUtility.copyProperties(request,employeeBandEntity);			
				employeeBandEntity.setStatus(1l);
				Date date = new Date();
				LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				employeeBandEntity.setCreated_date(localDate);
				employeeBandEntity=expenseTravelAdvanceDao.saveDetails(employeeBandEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			}
			
			
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}

	@Override
	public ExpanceTravelAdvanceEntity getExpenseTravelAdvenceDetails(Long employerid) {
		ExpanceTravelAdvanceEntity expanceTravelAdvanceEntities=null;
		String response="";
		try {
			expanceTravelAdvanceEntities=expenseTravelAdvanceDao.findByEmployerId(employerid);
			
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			//request.setResponse(response);
		}
		return expanceTravelAdvanceEntities;
	}	
	
	
	
	

	
}
