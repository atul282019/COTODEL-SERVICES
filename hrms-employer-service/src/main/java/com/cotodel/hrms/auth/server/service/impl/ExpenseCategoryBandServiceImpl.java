package com.cotodel.hrms.auth.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BandDao;
import com.cotodel.hrms.auth.server.dao.CategoryEmpBandDao;
import com.cotodel.hrms.auth.server.dao.ExpenseCategoryBandDao;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandRequest;
import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;
import com.cotodel.hrms.auth.server.service.ExpenseCategoryBandService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class ExpenseCategoryBandServiceImpl implements ExpenseCategoryBandService{

	@Autowired
	ExpenseCategoryBandDao  expenseCategoryBandDao;
	
	@Autowired
	CategoryEmpBandDao  categoryEmpBandDao;
	
	@Autowired
	BandDao  bandDao;

	@Override
	@Transactional
	public ExpenseCategoryBandRequest saveExpenseCategoryBandDetails(ExpenseCategoryBandRequest request) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);					
			
			ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
			CopyUtility.copyProperties(request,employeeBandEntity);			

			employeeBandEntity=expenseCategoryBandDao.saveDetails(employeeBandEntity);
			List<CategoryEmployeeBandEntity> list1=new ArrayList<CategoryEmployeeBandEntity>();
			List<CategoryEmployeeBandEntity> list2=new ArrayList<CategoryEmployeeBandEntity>();
			if(employeeBandEntity!=null) {
				List<CategoryEmployeeBandEntity> list=request.getList();
				for(CategoryEmployeeBandEntity categoryEmployeeBandEntity:list) {
					categoryEmployeeBandEntity.setExpenseCategoryId(employeeBandEntity.getId());
					list1.add(categoryEmployeeBandEntity);
				}
				
				list2=categoryEmpBandDao.saveDetails(list1);
				
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			
			
			request.setResponse(response);	
			
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;

	}


	@Override
	public ExpenseCategoryBandEntity getCompEmployeeBandDetails(String bandid) {
		ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
		
		String response=MessageConstant.RESPONSE_FAILED;
		
		try {
			employeeBandEntity=expenseCategoryBandDao.getEmployeeBandDetails(bandid);
			
			response=MessageConstant.RESPONSE_SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			response=MessageConstant.RESPONSE_FAILED;
			//companyEmployeeRequest.setResponse(response);
		}
		return employeeBandEntity;
	}


	@Override
	public ExpenseCategoryBandRequest getCompEmployeeBandDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}