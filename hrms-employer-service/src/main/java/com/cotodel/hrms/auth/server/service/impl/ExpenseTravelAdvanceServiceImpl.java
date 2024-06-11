package com.cotodel.hrms.auth.server.service.impl;

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
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);	
			employeeBandEntity=new ExpanceTravelAdvanceEntity();
			CopyUtility.copyProperties(request,employeeBandEntity);			
			employeeBandEntity.setStatus(1l);
			employeeBandEntity=expenseTravelAdvanceDao.saveDetails(employeeBandEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}

	@Override
	public List<ExpanceTravelAdvanceEntity> getExpenseTravelAdvenceDetailsList(Long employerid) {
		List<ExpanceTravelAdvanceEntity> expanceTravelAdvanceEntities=null;
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
	
	
	
	
//	@Override
//	@Transactional
//	public ExpenseTravelAdvanceRequest saveExpenseTravelAdvenceDetails(ExpenseTravelAdvanceRequest request) {
//		String response="";
//		try {
//			response=MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);					
//			
//			ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
//			
//			employeeBandEntity=expenseCategoryBandDao.findByEmployeeBandId(request.getId());
//			if(employeeBandEntity!=null) {
//				employeeBandEntity.setExpenseCategory(request.getExpenseCategory());
//				employeeBandEntity.setExpenseCode(request.getExpenseCode());
//				int update=expenseCategoryBandDao.updateDetails(employeeBandEntity);
//				System.out.println(update);
//			}else {
//				employeeBandEntity=new ExpenseCategoryBandEntity();
//				CopyUtility.copyProperties(request,employeeBandEntity);			
//				employeeBandEntity.setStatus(1);
//				employeeBandEntity=expenseCategoryBandDao.saveDetails(employeeBandEntity);
//			}
//			
//			List<CategoryEmployeeBandEntity> list1=new ArrayList<CategoryEmployeeBandEntity>();
//			List<CategoryEmployeeBandEntity> list2=new ArrayList<CategoryEmployeeBandEntity>();
//			if(employeeBandEntity!=null) {
//				List<CategoryEmployeeBandEntity> list=request.getList();
//				for(CategoryEmployeeBandEntity categoryEmployeeBandEntity:list) {
//					categoryEmployeeBandEntity.setExpenseCategoryId(employeeBandEntity.getId());
//					list1.add(categoryEmployeeBandEntity);
//				}
//				
//				list2=categoryEmpBandDao.saveDetails(list1);
//				
//				response=MessageConstant.RESPONSE_SUCCESS;
//			}
//			
//			
//			request.setResponse(response);	
//			
//		} catch (Exception e) {
//			response=MessageConstant.RESPONSE_FAILED;
//			e.printStackTrace();
//			request.setResponse(response);
//		}
//		return request;
//
//	}


//	@Override
//	public List<ExpanceTravelAdvanceEntity> getExpenseTravelAdvenceDetailsList(long employerid) {
//		List<ExpanceTravelAdvanceEntity> employeeBand=new ArrayList<ExpanceTravelAdvanceEntity>();
//		List<ExpenseTravelAdvanceRequest> expenseCategoryBandRequests=new ArrayList<ExpenseTravelAdvanceRequest>();
//		List<CategoryEmployeeBandEntity> categoryEmployeeBandEntity=new ArrayList<CategoryEmployeeBandEntity>();		
//		String response=MessageConstant.RESPONSE_FAILED;
//		List<ExpenseCategoryMasterEntity> expenseCategoryMasterEntities=null;
//		try {
//						
//		expenseCategoryMasterEntities=expenseCategoryMasterDao.getExpenseCategoryMaster();
//		if(expenseCategoryMasterEntities!=null) {
//			for (ExpenseCategoryMasterEntity expenseCategoryMasterEntity: expenseCategoryMasterEntities) {
//				
//				ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
//				employeeBandEntity=expenseCategoryBandDao.findByEmployeeBandIdWithEmployer(expenseCategoryMasterEntity.getId(),employerid);
//				if(employeeBandEntity!=null) {
//					
//				}else {
//					employeeBandEntity=new ExpenseCategoryBandEntity();
//					employeeBandEntity.setExpenseCategory(expenseCategoryMasterEntity.getExpenseCategory());
//					employeeBandEntity.setExpenseCode(expenseCategoryMasterEntity.getExpenseCode());
//					employeeBandEntity.setExpenseLimit(expenseCategoryMasterEntity.getExpenseLimit());
//					employeeBandEntity.setDayToExpiry(expenseCategoryMasterEntity.getDayToExpiry());
//					employeeBandEntity.setEmployerId(employerid);
//					employeeBandEntity.setMasterId(expenseCategoryMasterEntity.getId());
//					employeeBandEntity.setStatus(1);
//					employeeBandEntity=expenseCategoryBandDao.saveDetails(employeeBandEntity);
//				}
//				
//				
//			}
//		}
//			
//		employeeBand=expenseCategoryBandDao.findByEmployerId(employerid);
//		if(employeeBand!=null) {
//			for (ExpenseCategoryBandEntity employeeBandEntity: employeeBand) {
//				ExpenseCategoryBandRequest expenseCategoryBandRequest=new ExpenseCategoryBandRequest();
//				response=MessageConstant.RESPONSE_SUCCESS;
//				expenseCategoryBandRequest.setId(employeeBandEntity.getId());
//				expenseCategoryBandRequest.setBandFlag(employeeBandEntity.getBandFlag());
//				expenseCategoryBandRequest.setBandId(employeeBandEntity.getBandId());
//				expenseCategoryBandRequest.setDayToExpiry(employeeBandEntity.getDayToExpiry());
//				expenseCategoryBandRequest.setEmployerId(employeeBandEntity.getEmployerId());
//				expenseCategoryBandRequest.setExpenseCategory(employeeBandEntity.getExpenseCategory());
//				expenseCategoryBandRequest.setExpenseCode(employeeBandEntity.getExpenseCode());
//				expenseCategoryBandRequest.setExpenseLimit(employeeBandEntity.getExpenseLimit());
//				categoryEmployeeBandEntity=categoryEmpBandDao.getDetails(employeeBandEntity.getId());
//				expenseCategoryBandRequest.setList(categoryEmployeeBandEntity);
//				expenseCategoryBandRequests.add(expenseCategoryBandRequest);
//				
//			}
//		}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return expenseCategoryBandRequests;
//	}
	
	
}
