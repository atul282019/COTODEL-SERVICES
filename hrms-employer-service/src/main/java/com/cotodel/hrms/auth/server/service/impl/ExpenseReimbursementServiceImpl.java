package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cotodel.hrms.auth.server.dao.ExpenseReimbursementDao;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementRequest;
import com.cotodel.hrms.auth.server.model.ExpenseReimbursementEntity;
import com.cotodel.hrms.auth.server.repository.UploadSequenceRepository;
import com.cotodel.hrms.auth.server.service.ExpenseReimbursementService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class ExpenseReimbursementServiceImpl implements ExpenseReimbursementService{

	@Autowired
	ExpenseReimbursementDao  expenseReimbursementDao;
	
	@Autowired
	UploadSequenceRepository uploadSequenceRepository;
	
	@Override
	public ExpenseReimbursementEntity saveExpenseReimbursementFileUpload(ExpenseReimbursementRequest request) {
		String response="";
		ExpenseReimbursementEntity expenseReimbursementEntity=null;
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			expenseReimbursementEntity=new ExpenseReimbursementEntity();
			CopyUtility.copyProperties(request,expenseReimbursementEntity);			
			expenseReimbursementEntity.setStatus(0);
			Date date = new Date();
			LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			expenseReimbursementEntity.setCreated_date(localDate);
			String seq=sequenceID();
			expenseReimbursementEntity.setSequenceId(seq);
			//
			expenseReimbursementEntity=expenseReimbursementDao.saveDetails(expenseReimbursementEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return expenseReimbursementEntity;
	}

	@Override
    @Transactional(readOnly = true)
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

	@Override
	public List<ExpenseReimbursementEntity> getExpenseReimbFileByEmpID(Long employeeId) {
		
		List<ExpenseReimbursementEntity> list=new ArrayList<ExpenseReimbursementEntity>();
		List<ExpenseReimbursementEntity> list1=new ArrayList<ExpenseReimbursementEntity>();
		try {
			list=expenseReimbursementDao.getExpenseReimbursementDetailsList(employeeId);
			for (ExpenseReimbursementEntity expenseReimbursementEntity:list) {
				String message=getMessage(expenseReimbursementEntity.getStatus());
				expenseReimbursementEntity.setStatusMessage(message);
				list1.add(expenseReimbursementEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list1;
	}

	@Override
	public ExpenseReimbursementRequest getExpenseReimbursementFileDelete(ExpenseReimbursementRequest request) {
		// TODO Auto-generated method stub
				String response=MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);
				try {
					expenseReimbursementDao.deleteDetails(request.getId());
					response=MessageConstant.RESPONSE_SUCCESS;
					request.setResponse(response);
				} catch (Exception e) {
					// TODO: handle exception
					response=MessageConstant.RESPONSE_FAILED;
					request.setResponse(response);
				}
				
				
				return request;
	}

	@Override
	public ExpenseReimbursementEntity saveExpenseReimbursementFileUploadSubmit(ExpenseReimbursementRequest request) {
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
			String seq=sequenceID();
			expenseReimbursementEntity.setSequenceId(seq);
			//
			expenseReimbursementEntity=expenseReimbursementDao.saveDetails(expenseReimbursementEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return expenseReimbursementEntity;
	}

	 @Transactional
	 public Long fetchNextSequenceValue() {
	    return uploadSequenceRepository.getNextSeriesId();
	 }
	
	public String sequenceID() {
		
		String  sequence=String.valueOf(fetchNextSequenceValue());
		
		String sequenceValue="";
		String finalSequenceValue="";
		
		if(sequence.length()==1) {
			sequenceValue="00"+sequence;
		}else if(sequence.length()==2) {
			sequenceValue="0"+sequence;
		}else {
			sequenceValue=sequence;
		}
		
		finalSequenceValue=monthYear()+"-"+sequenceValue;
		
		return finalSequenceValue;
	}

	public String monthYear() {
		
		LocalDate date = LocalDate.now();		
		String month=String.valueOf(date.getMonthValue());
		String year=String.valueOf(date.getYear());
		String str="CDL-";
		String  monthValue=month.length()==1?"0"+month:month;
		String  yearValue=year.substring(2,4);
		
		return str+monthValue+yearValue;
	}

	public String getMessage(Long status) {

		int st = status.intValue();		
		String message = "";		
		switch (st) {
		case 0:
			message = "Draft";
			break;
		case 1:
			message = "Submitted";
			break;
		case 2:
			message = "InProgress";
			break;
		default:
			message = "Draft";
		}
		return message;
	}
}
