package com.cotodel.hrms.auth.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BandDao;
import com.cotodel.hrms.auth.server.dao.CategoryEmpBandDao;
import com.cotodel.hrms.auth.server.dao.ExpenseBandNumberDao;
import com.cotodel.hrms.auth.server.dao.ExpenseCategoryBandDao;
import com.cotodel.hrms.auth.server.dao.ExpenseCategoryMasterDao;
import com.cotodel.hrms.auth.server.dto.ExpenseCategoryBandRequest;
import com.cotodel.hrms.auth.server.model.CategoryEmployeeBandEntity;
import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryBandEntity;
import com.cotodel.hrms.auth.server.model.ExpenseCategoryMasterEntity;
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
	
	@Autowired
	ExpenseCategoryMasterDao  expenseCategoryMasterDao;
	
	@Autowired
	ExpenseBandNumberDao  expenseBandNumberDao;
	
	@Override
	@Transactional
	public ExpenseCategoryBandRequest saveExpenseCategoryBandDetails(ExpenseCategoryBandRequest request) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);					
			
			ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
			
			employeeBandEntity=expenseCategoryBandDao.findByEmployeeBandId(request.getId());
			if(employeeBandEntity!=null) {
				employeeBandEntity.setExpenseCategory(request.getExpenseCategory());
				employeeBandEntity.setExpenseCode(request.getExpenseCode());
				employeeBandEntity.setDayToExpiry(request.getDayToExpiry());
				employeeBandEntity.setExpenseLimit(request.getExpenseLimit());
				int update=expenseCategoryBandDao.updateDetails(employeeBandEntity);
				System.out.println(update);
			}else {
				employeeBandEntity=new ExpenseCategoryBandEntity();
				CopyUtility.copyProperties(request,employeeBandEntity);			
				employeeBandEntity.setStatus(1);
				employeeBandEntity=expenseCategoryBandDao.saveDetails(employeeBandEntity);
			}
			
			List<CategoryEmployeeBandEntity> list1=new ArrayList<CategoryEmployeeBandEntity>();
			List<CategoryEmployeeBandEntity> list2=new ArrayList<CategoryEmployeeBandEntity>();
			List<CategoryEmployeeBandEntity> list3=new ArrayList<CategoryEmployeeBandEntity>();
			if(employeeBandEntity!=null) {
				//delete in case old data exist
				list3=categoryEmpBandDao.getDetails(employeeBandEntity.getId());
				if(list3!=null) {
					for(CategoryEmployeeBandEntity categoryEmployeeBandEntity:list3) {
						categoryEmpBandDao.deleteById(categoryEmployeeBandEntity.getId());
					}
				}
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
			//e.printStackTrace();
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
			//e.printStackTrace();
			response=MessageConstant.RESPONSE_FAILED;
			//companyEmployeeRequest.setResponse(response);
		}
		return employeeBandEntity;
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
//			//e.printStackTrace();
//			response=MessageConstant.RESPONSE_FAILED;
//			//companyEmployeeRequest.setResponse(response);
//		}
//		return expenseCategoryBandRequest;
//	}


	@Override
	public ExpenseCategoryBandRequest getCompEmployeeBandDetailsId(Long id,Long employerId) {
		ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
		ExpenseCategoryBandRequest expenseCategoryBandRequest=new ExpenseCategoryBandRequest();
		List<CategoryEmployeeBandEntity> categoryEmployeeBandEntity=new ArrayList<CategoryEmployeeBandEntity>();

		String response=MessageConstant.RESPONSE_FAILED;
		
		try {
			employeeBandEntity=expenseCategoryBandDao.findByEmployeeBandId(id);
			if(employeeBandEntity!=null) {
				response=MessageConstant.RESPONSE_SUCCESS;
				expenseCategoryBandRequest.setId(employeeBandEntity.getId());
				expenseCategoryBandRequest.setBandFlag(employeeBandEntity.getBandFlag());
				expenseCategoryBandRequest.setBandId(employeeBandEntity.getBandId());
				expenseCategoryBandRequest.setDayToExpiry(employeeBandEntity.getDayToExpiry());
				expenseCategoryBandRequest.setEmployerId(employeeBandEntity.getEmployerId());
				expenseCategoryBandRequest.setExpenseCategory(employeeBandEntity.getExpenseCategory());
				expenseCategoryBandRequest.setExpenseCode(employeeBandEntity.getExpenseCode());
				expenseCategoryBandRequest.setExpenseLimit(employeeBandEntity.getExpenseLimit());
				expenseCategoryBandRequest.setStatus(employeeBandEntity.getStatus());
				categoryEmployeeBandEntity=categoryEmpBandDao.getDetails(employeeBandEntity.getId());
				expenseCategoryBandRequest.setList(categoryEmployeeBandEntity);
				
			}
			
			
			expenseCategoryBandRequest.setResponse(response);
		} catch (Exception e) {
			//e.printStackTrace();
			response=MessageConstant.RESPONSE_FAILED;
			//companyEmployeeRequest.setResponse(response);
		}
		return expenseCategoryBandRequest;
	}


	@Override
	public List<ExpenseCategoryBandRequest> getCompEmployeeBandDetailsList(long employerid) {
		List<ExpenseCategoryBandEntity> employeeBand=new ArrayList<ExpenseCategoryBandEntity>();
		List<ExpenseCategoryBandRequest> expenseCategoryBandRequests=new ArrayList<ExpenseCategoryBandRequest>();
		List<CategoryEmployeeBandEntity> categoryEmployeeBandEntity=new ArrayList<CategoryEmployeeBandEntity>();		
		String response=MessageConstant.RESPONSE_FAILED;
		List<ExpenseCategoryMasterEntity> expenseCategoryMasterEntities=null;
		try {
						
		expenseCategoryMasterEntities=expenseCategoryMasterDao.getExpenseCategoryMaster();
		if(expenseCategoryMasterEntities!=null) {
			for (ExpenseCategoryMasterEntity expenseCategoryMasterEntity: expenseCategoryMasterEntities) {
				
				ExpenseCategoryBandEntity employeeBandEntity=new ExpenseCategoryBandEntity();
				employeeBandEntity=expenseCategoryBandDao.findByEmployeeBandIdWithEmployer(expenseCategoryMasterEntity.getId(),employerid);
				if(employeeBandEntity!=null) {
					
				}else {
					employeeBandEntity=new ExpenseCategoryBandEntity();
					employeeBandEntity.setExpenseCategory(expenseCategoryMasterEntity.getExpenseCategory());
					employeeBandEntity.setExpenseCode(expenseCategoryMasterEntity.getExpenseCode());
					employeeBandEntity.setExpenseLimit(expenseCategoryMasterEntity.getExpenseLimit());
					employeeBandEntity.setDayToExpiry(expenseCategoryMasterEntity.getDayToExpiry());
					employeeBandEntity.setEmployerId(employerid);
					employeeBandEntity.setMasterId(expenseCategoryMasterEntity.getId());
					employeeBandEntity.setStatus(1);
					employeeBandEntity=expenseCategoryBandDao.saveDetails(employeeBandEntity);
				}
				
				
			}
		}
			
		employeeBand=expenseCategoryBandDao.findByEmployerId(employerid);
		if(employeeBand!=null) {
			for (ExpenseCategoryBandEntity employeeBandEntity: employeeBand) {
				ExpenseCategoryBandRequest expenseCategoryBandRequest=new ExpenseCategoryBandRequest();
				response=MessageConstant.RESPONSE_SUCCESS;
				expenseCategoryBandRequest.setId(employeeBandEntity.getId());
				expenseCategoryBandRequest.setBandFlag(employeeBandEntity.getBandFlag());
				expenseCategoryBandRequest.setBandId(employeeBandEntity.getBandId());
				expenseCategoryBandRequest.setDayToExpiry(employeeBandEntity.getDayToExpiry());
				expenseCategoryBandRequest.setEmployerId(employeeBandEntity.getEmployerId());
				expenseCategoryBandRequest.setExpenseCategory(employeeBandEntity.getExpenseCategory());
				expenseCategoryBandRequest.setExpenseCode(employeeBandEntity.getExpenseCode());
				expenseCategoryBandRequest.setExpenseLimit(employeeBandEntity.getExpenseLimit());
				if(employeeBandEntity.getMasterId()!=0) {
					expenseCategoryBandRequest.setFlag("D");
				}
				categoryEmployeeBandEntity=categoryEmpBandDao.getDetails(employeeBandEntity.getId());
				expenseCategoryBandRequest.setList(categoryEmployeeBandEntity);
				expenseCategoryBandRequests.add(expenseCategoryBandRequest);
				
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expenseCategoryBandRequests;
	}


	@Override
	public ExpenseCategoryBandRequest deleteExpenseCategoryBandDetails(ExpenseCategoryBandRequest request) {
		// TODO Auto-generated method stub
		String response=MessageConstant.RESPONSE_FAILED;
		request.setResponse(response);
		try {
			expenseCategoryBandDao.deleteDetails(request.getId());
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
	public List<ExpenseBandNumberEntity> getExpenseBandList(Long employerId) {
		ExpenseBandNumberEntity expenseBandNumberEntity=new ExpenseBandNumberEntity();
		List<ExpenseBandNumberEntity> list=new ArrayList<ExpenseBandNumberEntity>();
		try {
			expenseBandNumberEntity=expenseBandNumberDao.findByEmployerId(employerId);
			if(expenseBandNumberEntity!=null) {
				list.add(expenseBandNumberEntity);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return list;
	}


	@Override
	public String getCompEmployeeBandId(Long id) {
		// TODO Auto-generated method stub
		ExpenseCategoryBandEntity employeeBandEntity=null;
		String response="";
		String expenseLimit="";
		try {
			employeeBandEntity=expenseCategoryBandDao.findByEmployeeBandId(id);
			if(employeeBandEntity!=null) {
				//response=MessageConstant.RESPONSE_SUCCESS;
				expenseLimit= employeeBandEntity.getExpenseLimit();
				
			}
			
			
		} catch (Exception e) {
			//e.printStackTrace();
			//response=MessageConstant.RESPONSE_FAILED;
			//companyEmployeeRequest.setResponse(response);
		}
		return expenseLimit;
	}
	
	
}
