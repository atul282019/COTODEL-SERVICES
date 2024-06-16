package com.cotodel.hrms.auth.server.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.BandDao;
import com.cotodel.hrms.auth.server.dao.EmployeeBandAddTierDao;
import com.cotodel.hrms.auth.server.dao.EmployeeBandAddTierReviewDao;
import com.cotodel.hrms.auth.server.dao.EmployeeBandDao;
import com.cotodel.hrms.auth.server.dao.ExpenseBandNumberDao;
import com.cotodel.hrms.auth.server.dto.EmployeeBandAddTierRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeBandAddTierReviewRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeBandNameRequest;
import com.cotodel.hrms.auth.server.dto.EmployeeBandRequest;
import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierEntity;
import com.cotodel.hrms.auth.server.model.EmployeeBandAddTierReviewEntity;
import com.cotodel.hrms.auth.server.model.EmployeeBandEntity;
import com.cotodel.hrms.auth.server.model.ExpenseBandNumberEntity;
import com.cotodel.hrms.auth.server.service.EmployeeBandService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeBandServiceImpl implements EmployeeBandService{

	@Autowired
	EmployeeBandDao  employeeBandDao;
	
	@Autowired
	BandDao  bandDao;

	@Autowired
	EmployeeBandAddTierDao  employeeBandAddTierDao;
	
	@Autowired
	EmployeeBandAddTierReviewDao  employeeBandAddTierReviewDao;
	
	@Autowired
	ExpenseBandNumberDao  expenseBandNumberDao;
	
	@Override
	@Transactional
	public EmployeeBandRequest saveCompEmployeeBandDetails(EmployeeBandRequest request) {
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);			
			
			
			EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
			CopyUtility.copyProperties(request,employeeBandEntity);
			
				employeeBandEntity=employeeBandDao.saveDetails(employeeBandEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			
			
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;

	}


//	@Override
//	public EmployeeBandEntity getCompEmployeeBandDetails(String bandid) {
//		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
//		
//		String response=MessageConstant.RESPONSE_FAILED;
//		
//		try {
//			employeeBandEntity=employeeBandDao.getEmployeeBandDetails(bandid);
//			
//			response=MessageConstant.RESPONSE_SUCCESS;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			response=MessageConstant.RESPONSE_FAILED;
//			//companyEmployeeRequest.setResponse(response);
//		}
//		return employeeBandEntity;
//	}


	@Override
	public List<EmployeeBandEntity> getEmployeeBandList() {
		List<EmployeeBandEntity> employeeBandEntity=new ArrayList<EmployeeBandEntity>();
		
		String response=MessageConstant.RESPONSE_FAILED;
		
		try {
			employeeBandEntity=employeeBandDao.getEmployeeBandList();
			
			response=MessageConstant.RESPONSE_SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			response=MessageConstant.RESPONSE_FAILED;
			//companyEmployeeRequest.setResponse(response);
		}
		return employeeBandEntity;
	}


	@Override
	public EmployeeBandAddTierRequest saveEmployeeBandAddTier(EmployeeBandAddTierRequest request) {
		String response=MessageConstant.RESPONSE_FAILED;
		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
		List<EmployeeBandAddTierEntity> list1=new ArrayList<EmployeeBandAddTierEntity>();
		List<EmployeeBandAddTierEntity> list2=new ArrayList<EmployeeBandAddTierEntity>();
		ExpenseBandNumberEntity expenseBandNumberEntity=new ExpenseBandNumberEntity();
		try {
			//
			
			// delete old data;
			employeeBandEntity=employeeBandDao.getEmployeeBandId(request.getEmployerId());
			if(employeeBandEntity!=null) {
				employeeBandDao.deleteDetails(employeeBandEntity.getId());
				employeeBandAddTierDao.deleteDetails(employeeBandEntity.getId());
				expenseBandNumberEntity=expenseBandNumberDao.findByEmployerId(request.getEmployerId());
				if(expenseBandNumberEntity!=null) {
					expenseBandNumberDao.deleteDetails(expenseBandNumberEntity.getId());
				}
			}
			
			//
			employeeBandEntity=new EmployeeBandEntity();
			CopyUtility.copyProperties(request,employeeBandEntity);
			employeeBandEntity.setStatus(1);
				employeeBandEntity=employeeBandDao.saveDetails(employeeBandEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
				request.setResponse(response);
			//
			
			if(employeeBandEntity!=null) {
				
				
				
				
				String alpha="Alphabetical";
				if(employeeBandEntity.getEmployeeBandNoAlpha()!=null && employeeBandEntity.getEmployeeBandNoAlpha().equalsIgnoreCase("Numeric")) {
					alpha="Numeric";
				}
				
				List<EmployeeBandAddTierEntity> list=request.getList();
				int k=1;
				expenseBandNumberEntity=new ExpenseBandNumberEntity();
				CopyUtility.copyProperties(request,expenseBandNumberEntity);
				expenseBandNumberEntity.setStatus(1);
				
				for(EmployeeBandAddTierEntity employeeBandAddTierEntity:list) {
					if(k==1) {
						expenseBandNumberEntity.setBandNameOne(employeeBandAddTierEntity.getEmployeeBand());
					}else if(k==2) {
						expenseBandNumberEntity.setBandNameTwo(employeeBandAddTierEntity.getEmployeeBand());
					}else if(k==3) {
						expenseBandNumberEntity.setBandNameThree(employeeBandAddTierEntity.getEmployeeBand());
					}else if(k==4) {
						expenseBandNumberEntity.setBandNameFour(employeeBandAddTierEntity.getEmployeeBand());
					}else if(k==5) {
						expenseBandNumberEntity.setBandNameFive(employeeBandAddTierEntity.getEmployeeBand());
					}else if(k==6) {
						expenseBandNumberEntity.setBandNameSix(employeeBandAddTierEntity.getEmployeeBand());
					}
					k++;
				}
				expenseBandNumberEntity=expenseBandNumberDao.saveDetails(expenseBandNumberEntity);
				String alphabetic="";
				int j=1;
				for(EmployeeBandAddTierEntity employeeBandAddTierEntity:list) {
					
					if(alpha.equalsIgnoreCase("Alphabetical")) {
						if(j==1) {
							alphabetic="A";
						}else if(j==2) {
							alphabetic="B";
						}else if(j==3) {
							alphabetic="C";
						}else if(j==4) {
							alphabetic="D";
						}else if(j==5) {
							alphabetic="E";
						}else if(j==6) {
							alphabetic="F";
						}
					}else {
						alphabetic=""+j;
					}
					employeeBandAddTierEntity.setEmployeeBandId(employeeBandEntity.getId());
					int total=employeeBandAddTierEntity.getAdditionalTiers();
					
					for(int i=1;i<=total;i++) {
						if(alpha.equalsIgnoreCase("Alphabetical")) {
						if(i==1) {
							employeeBandAddTierEntity.setAdditionalTiersOne(alphabetic+i);
						}else if(i==2){
							employeeBandAddTierEntity.setAdditionalTiersTwo(alphabetic+i);
						}else if(i==3){
							employeeBandAddTierEntity.setAdditionalTiersThree(alphabetic+i);
						}else if(i==4) {
							employeeBandAddTierEntity.setAdditionalTiersFour(alphabetic+i);
						}else if(i==5) {
							employeeBandAddTierEntity.setAdditionalTiersFive(alphabetic+i);
						}
					}else {
						if(i==1) {
							alphabetic="A";
							employeeBandAddTierEntity.setAdditionalTiersOne(j+alphabetic);
						}else if(i==2){
							alphabetic="B";
							employeeBandAddTierEntity.setAdditionalTiersTwo(j+alphabetic);
						}else if(i==3){
							alphabetic="C";
							employeeBandAddTierEntity.setAdditionalTiersThree(j+alphabetic);
						}else if(i==4) {
							alphabetic="D";
							employeeBandAddTierEntity.setAdditionalTiersFour(j+alphabetic);
						}else if(i==5) {
							alphabetic="E";
							employeeBandAddTierEntity.setAdditionalTiersFive(j+alphabetic);
						}
					}
					}
					list1.add(employeeBandAddTierEntity);
					j++;
				}
				
				list2=employeeBandAddTierDao.saveDetails(list1);
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			request.setResponse(response);	
		}catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}


	@Override
	public EmployeeBandAddTierReviewRequest saveEmployeeBandAddTierReview(EmployeeBandAddTierReviewRequest request) {
		String response=MessageConstant.RESPONSE_FAILED;
		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
		List<EmployeeBandAddTierReviewEntity> list1=new ArrayList<EmployeeBandAddTierReviewEntity>();
		List<EmployeeBandAddTierReviewEntity> list2=new ArrayList<EmployeeBandAddTierReviewEntity>();
		try {
			employeeBandEntity=employeeBandDao.getEmployeeBandId(request.getEmployerId());
			if(employeeBandEntity!=null) {
				List<EmployeeBandAddTierReviewEntity> list=request.getList();
				for(EmployeeBandAddTierReviewEntity employeeBandAddTierEntity:list) {
					employeeBandAddTierEntity.setEmployeeBandId(employeeBandAddTierEntity.getId());
					list1.add(employeeBandAddTierEntity);
				}
				
				list2=employeeBandAddTierReviewDao.saveDetails(list1);
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			request.setResponse(response);	
		}catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			e.printStackTrace();
			request.setResponse(response);
		}
		return request;
	}


	@Override
	public EmployeeBandAddTierRequest getEmployeeBandAddTierReview(Long employerId) {
		String response=MessageConstant.RESPONSE_FAILED;
		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
		List<EmployeeBandAddTierEntity> list=new ArrayList<EmployeeBandAddTierEntity>();
		EmployeeBandAddTierRequest employeeBandAddTierRequest=new EmployeeBandAddTierRequest();
		try {
			employeeBandEntity=employeeBandDao.getEmployeeBandId(employerId);
			if(employeeBandEntity!=null) {
				employeeBandAddTierRequest.setBandEnabled(employeeBandEntity.getBandEnabled());
				employeeBandAddTierRequest.setEmployeeBandNo(employeeBandEntity.getEmployeeBandNo());
				employeeBandAddTierRequest.setEmployeeBandNoAlpha(employeeBandEntity.getEmployeeBandNoAlpha());
				employeeBandAddTierRequest.setEmployeeBandOrder(employeeBandEntity.getEmployeeBandOrder());
				employeeBandAddTierRequest.setEmployerId(employeeBandEntity.getEmployerId());
				employeeBandAddTierRequest.setIntroAddTierFlag(employeeBandEntity.getIntroAddTierFlag());
				employeeBandAddTierRequest.setStatus(employeeBandEntity.getStatus());
				list=employeeBandAddTierDao.getDetails(employeeBandEntity.getId());
				employeeBandAddTierRequest.setList(list);
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			employeeBandAddTierRequest.setResponse(response);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeBandAddTierRequest;
	}


	@Override
	public EmployeeBandAddTierRequest getEmployeeBandAddTierDisable(Long employerId) {
		String response=MessageConstant.RESPONSE_FAILED;
		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
		EmployeeBandAddTierRequest employeeBandAddTierRequest=new EmployeeBandAddTierRequest();
		try {
			employeeBandEntity=employeeBandDao.findByEmployeeBandED(employerId);
			if(employeeBandEntity!=null) {				
				employeeBandEntity.setStatus(0);
				employeeBandEntity=employeeBandDao.saveDetails(employeeBandEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			employeeBandAddTierRequest.setResponse(response);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeBandAddTierRequest;
	}


	@Override
	public EmployeeBandAddTierRequest getEmployeeBandAddTierEnaable(Long employerId) {
		String response=MessageConstant.RESPONSE_FAILED;
		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
		EmployeeBandAddTierRequest employeeBandAddTierRequest=new EmployeeBandAddTierRequest();
		try {
			employeeBandEntity=employeeBandDao.findByEmployeeBandED(employerId);
			if(employeeBandEntity!=null) {				
				employeeBandEntity.setStatus(1);
				employeeBandEntity=employeeBandDao.saveDetails(employeeBandEntity);
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			employeeBandAddTierRequest.setResponse(response);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeBandAddTierRequest;
	}


	@Override
	public EmployeeBandNameRequest getEmployeeBandName(Long employerId) {
		String response=MessageConstant.RESPONSE_FAILED;
		EmployeeBandEntity employeeBandEntity=new EmployeeBandEntity();
		List<EmployeeBandAddTierEntity> list=new ArrayList<EmployeeBandAddTierEntity>();
		EmployeeBandAddTierRequest employeeBandAddTierRequest=new EmployeeBandAddTierRequest();
		List<String> listBandName=new ArrayList<String>();
		EmployeeBandNameRequest employeeBandNameRequest=new EmployeeBandNameRequest();
		try {
			employeeBandEntity=employeeBandDao.getEmployeeBandId(employerId);
			int i=1;
			if(employeeBandEntity!=null) {				
				list=employeeBandAddTierDao.getDetails(employeeBandEntity.getId());
				for(EmployeeBandAddTierEntity employeeBandAddTierEntity:list) {
					//System.out.println();
					listBandName.add(employeeBandAddTierEntity.getEmployeeBand());
					if(i==1) {
						employeeBandNameRequest.setBandNameOne(employeeBandAddTierEntity.getEmployeeBand());
					}else if(i==2) {
						employeeBandNameRequest.setBandNameTwo(employeeBandAddTierEntity.getEmployeeBand());
					}else if(i==3) {
						employeeBandNameRequest.setBandNameThree(employeeBandAddTierEntity.getEmployeeBand());
					}else if(i==4) {
						employeeBandNameRequest.setBandNameFour(employeeBandAddTierEntity.getEmployeeBand());
					}else if(i==5) {
						employeeBandNameRequest.setBandNameFive(employeeBandAddTierEntity.getEmployeeBand());
					}else if(i==6) {
						employeeBandNameRequest.setBandNameSix(employeeBandAddTierEntity.getEmployeeBand());
					}
					i++;
				}
				employeeBandNameRequest.setEmployerId(employerId);
				response=MessageConstant.RESPONSE_SUCCESS;
			}
			employeeBandNameRequest.setResponse(response);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return employeeBandNameRequest;
	}
	
}
