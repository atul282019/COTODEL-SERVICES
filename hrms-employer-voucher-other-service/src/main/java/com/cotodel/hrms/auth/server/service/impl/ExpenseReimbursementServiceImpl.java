package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cotodel.hrms.auth.server.dao.ExpenseReimbursementDao;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementDto;
import com.cotodel.hrms.auth.server.dto.ExpenseReimbursementFileDto;
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
			expenseReimbursementEntity.setCreated_date(LocalDateTime.now());
			String seq=sequenceID();
			expenseReimbursementEntity.setSequenceId(seq);
			//
			expenseReimbursementEntity=expenseReimbursementDao.saveDetails(expenseReimbursementEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
			
		} catch (Exception e) {
			//e.printStackTrace();
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
			//e.printStackTrace();
			//request.setResponse(response);
		}
		return expenseReimbursementEntity;
	}

	@Override
	public List<ExpenseReimbursementFileDto> getExpenseReimbFileByEmpID(Long employeeId) {
		
		List<ExpenseReimbursementFileDto> list=new ArrayList<ExpenseReimbursementFileDto>();
		List<ExpenseReimbursementFileDto> list1=new ArrayList<ExpenseReimbursementFileDto>();
		try {
			list=expenseReimbursementDao.getExpenseReimbursementDetailsList(employeeId);
			for (ExpenseReimbursementFileDto expenseReimbursementFileDto:list) {
				String message=getMessage(expenseReimbursementFileDto.getStatus());
				
				expenseReimbursementFileDto.setStatusMessage(message);
				//change
				list1.add(expenseReimbursementFileDto);
			}
		} catch (Exception e) {
			//e.printStackTrace();
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
			expenseReimbursementEntity.setWorkFlowId(100013l);
			Date date = new Date();
			LocalDate localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			expenseReimbursementEntity.setCreated_date(LocalDateTime.now());
			String seq=sequenceID();
			expenseReimbursementEntity.setSequenceId(seq);
			//
			expenseReimbursementEntity=expenseReimbursementDao.saveDetails(expenseReimbursementEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			//e.printStackTrace();
			// TODO: handle exception
		}
		return expenseReimbursementEntity;
	}

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
			message = "Pending";
			break;
		case 2:
			message = "Rejected";
			break;
		case 3:
			message = "Approved";
			break;
		default:
			message = "Draft";
		}
		return message;
	}

	@Override
	public List<ExpenseReimbursementFileDto> getExpenseReimbFileByEmpAndEmprId(Long employerId, Long employeeId) {
		List<ExpenseReimbursementFileDto> list=new ArrayList<ExpenseReimbursementFileDto>();
		List<ExpenseReimbursementFileDto> list1=new ArrayList<ExpenseReimbursementFileDto>();
		try {
			if(employerId>0) {
				list=expenseReimbursementDao.getExpenseReimListByEmplrId(employerId);
			}else {
				list=expenseReimbursementDao.getExpenseReimListByEmpId(employeeId);
			}
			
			for (ExpenseReimbursementFileDto expenseReimbursementDto:list) {
				ExpenseReimbursementEntity expenseReimbursementEntity=new ExpenseReimbursementEntity();
				
				String message=getMessage(expenseReimbursementDto.getStatus());
				String approvedBy=expenseReimbursementDto.getApprovedBy()==null?"":expenseReimbursementDto.getApprovedBy();
				expenseReimbursementDto.setStatusMessage(message);
				expenseReimbursementDto.setApprovedBy(approvedBy);
				list1.add(expenseReimbursementDto);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return list1;
	}

	@Override
	public ExpenseReimbursementDto getExpenseReimbFileById(Long id) {
		// TODO Auto-generated method stub
		ExpenseReimbursementDto expenseReimbursementDto=expenseReimbursementDao.getExpenseReimById(id);
		String message=getMessage(expenseReimbursementDto.getStatus());
		String approvedBy=expenseReimbursementDto.getApprovedBy()==null?"Pending":expenseReimbursementDto.getApprovedBy();
		expenseReimbursementDto.setStatusMessage(message);
		expenseReimbursementDto.setApprovedBy(approvedBy);
		return expenseReimbursementDto;
	}

	@Override
	public ExpenseReimbursementEntity updateExpenseReimbursementApprover(ExpenseReimbursementRequest request) {
		String response="";
		ExpenseReimbursementEntity expenseReimbursementEntity=new ExpenseReimbursementEntity();;
		try {
			response = MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
			expenseReimbursementEntity = expenseReimbursementDao.getExpenseDetails(request.getId());
			if (expenseReimbursementEntity != null) {

				// CopyUtility.copyProperties(request,expenseReimbursementEntity);
				String remarks = "";
				if(request.getApprovedOrRejected() != null) {
					if (request.getApprovedOrRejected().equalsIgnoreCase(MessageConstant.APPROVED)) {
						expenseReimbursementEntity.setStatus(3l);
						expenseReimbursementEntity.setWorkFlowId(100014l);
						expenseReimbursementEntity.setApprovedAmount(request.getApprovedAmount());
						//remarks = expenseReimbursementEntity.getRemarks() + "," + request.getRemarks();
						//expenseReimbursementEntity.setRemarks(remarks);
					} else {
	
						expenseReimbursementEntity.setStatus(2l);
						expenseReimbursementEntity.setWorkFlowId(100015l);
						remarks = request.getRejectedRemarks();
						expenseReimbursementEntity.setRejectedRemarks(remarks);
					}
				}
				Date date = new Date();
				LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				expenseReimbursementEntity.setUpdatedDate(localDate);
				expenseReimbursementEntity.setApprovedDate(localDate);
				expenseReimbursementEntity.setApprovedBy(request.getUsername());
				expenseReimbursementEntity = expenseReimbursementDao.saveDetails(expenseReimbursementEntity);
				response = MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			} else {
				response = MessageConstant.RESPONSE_FAILED;
				request.setResponse(response);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			// TODO: handle exception
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return expenseReimbursementEntity;
	}
	
}
