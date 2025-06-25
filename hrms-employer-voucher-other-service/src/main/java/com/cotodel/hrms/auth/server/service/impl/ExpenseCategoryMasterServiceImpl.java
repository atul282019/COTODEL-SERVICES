package com.cotodel.hrms.auth.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.ExpenseCategoryMasterDao;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryMasterEntity;
import com.cotodel.hrms.auth.server.service.ExpenseCategoryMasterService;
@Repository
public class ExpenseCategoryMasterServiceImpl implements ExpenseCategoryMasterService{

	@Autowired
	ExpenseCategoryMasterDao  expenseCategoryMasterDao;

	@Override
	public List<ExpenseCategoryMasterEntity> getExpenseCategoryMaster() {
		// TODO Auto-generated method stub
		return expenseCategoryMasterDao.getExpenseCategoryMaster();
	}
	
	
	

//	@Override
//	public ExpenseCategoryBandRequest getCompEmployeeBandDetails() {
//		ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
//		ExpenseCategoryBandRequest expenseCategoryBandRequest=new ExpenseCategoryBandRequest();
//		List<CategoryEmployeeBandEntity> categoryEmployeeBandEntity=new ArrayList<CategoryEmployeeBandEntity>();
//
//		String response=MessageConstant.RESPONSE_FAILED;
//		
//		try {
//			employeeBandEntity=expenseCategoryBandDao.findByEmployeeBandId();
//			if(employeeBandEntity!=null) {
//				expenseCategoryBandRequest.setBandFlag(employeeBandEntity.getBandFlag());
//				expenseCategoryBandRequest.setBandId(employeeBandEntity.getBandId());
//				expenseCategoryBandRequest.setDayToExpiry(employeeBandEntity.getDayToExpiry());
//				expenseCategoryBandRequest.setEmployerId(employeeBandEntity.getEmployerId());
//				expenseCategoryBandRequest.setExpenseCategory(employeeBandEntity.getExpenseCategory());
//				expenseCategoryBandRequest.setExpenseCode(employeeBandEntity.getExpenseCode());
//				categoryEmployeeBandEntity=categoryEmpBandDao.getDetails(employeeBandEntity.getId());
//				expenseCategoryBandRequest.setList(categoryEmployeeBandEntity);
//			}
//			
//			response=MessageConstant.RESPONSE_SUCCESS;
//			expenseCategoryBandRequest.setResponse(response);
//		} catch (Exception e) {
//			e.printStackTrace();
//			response=MessageConstant.RESPONSE_FAILED;
//			//companyEmployeeRequest.setResponse(response);
//		}
//		return expenseCategoryBandRequest;
//	}
//	
	
}
