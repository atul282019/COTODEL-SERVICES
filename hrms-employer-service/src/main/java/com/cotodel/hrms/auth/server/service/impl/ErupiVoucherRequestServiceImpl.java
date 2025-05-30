package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingDao;
import com.cotodel.hrms.auth.server.dao.ErupiVoucherRequestDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingManagerRequest;
import com.cotodel.hrms.auth.server.dto.ErupiVoucherCreateRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingEntity;
import com.cotodel.hrms.auth.server.model.ErupiVoucherCreationRequestEntity;
import com.cotodel.hrms.auth.server.service.ErupiVoucherRequestService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class ErupiVoucherRequestServiceImpl implements ErupiVoucherRequestService{


	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherRequestServiceImpl.class);
	@Autowired
	ErupiVoucherRequestDao  erupiVoucherRequestDao;
	
	@Autowired
	EmployeeOnboardingDao  employeeOnboardingDao;
	
	@Override
	public ErupiVoucherCreateRequest saveErupiVoucherRequest(ErupiVoucherCreateRequest request) {
		// TODO Auto-generated method stub
		ErupiVoucherCreationRequestEntity erupiVoucherCreationRequestEntity=null;
		String response=MessageConstant.RESPONSE_FAILED;
		try {
//			String amount= request.getAmount().toString();
//			String amt=formatAmount(amount);
			String dataString =
	        		//added String.valueOf because it was summing employerid and employeeid
	        		String.valueOf(request.getEmployerId()) +String.valueOf(request.getEmployeeId()) +request.getPurposeCode()+
	        	    request.getMcc()+request.getName()+request.getVoucherType()+request.getVoucherSubType()+
	        	    request.getMobile()+request.getAmount()+request.getRemarks()+
					request.getClientKey()+MessageConstant.SECRET_KEY;
			log.info("dataString::"+dataString);
			String hash=ValidateConstants.generateHash(dataString);
			log.info("hash::"+hash);
			if(!request.getHash().equalsIgnoreCase(hash)) {
				request.setResponse(MessageConstant.HASH_ERROR);
				return request;
			}
			Float amount = Float.parseFloat(request.getAmount());
			if(amount<=0) {
				response=MessageConstant.INSUFBAL;
				request.setResponse(response);
				return request;
			}
			erupiVoucherCreationRequestEntity=new ErupiVoucherCreationRequestEntity();
			CopyUtility.copyProperties(request,erupiVoucherCreationRequestEntity);
			erupiVoucherCreationRequestEntity.setCreationDate(LocalDateTime.now());
			erupiVoucherCreationRequestEntity.setStatus(0);
			erupiVoucherCreationRequestEntity.setAmount(amount);
			erupiVoucherCreationRequestEntity.setStatusMessage("Requested");
			erupiVoucherCreationRequestEntity=erupiVoucherRequestDao.saveDetails(erupiVoucherCreationRequestEntity);
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}


	@Override
	public List<ErupiVoucherCreationRequestEntity> getErupiVoucherRequestEmployerId(Long EmployerId,Long EmployeeId) {
		// TODO Auto-generated method stub
		
		if(EmployerId!=null && EmployerId>0) {
			return erupiVoucherRequestDao.getVoucherCreationRequest(EmployerId);
		}else {
			return erupiVoucherRequestDao.getVoucherCreationRequestEmp(EmployeeId);
		}
	}
	public static String formatAmount(String amount) {
        // Convert the string to a double
        double value = Double.parseDouble(amount);

        // Check if it's an integer (i.e., no decimal value)
        if (value == (int) value) {
            return String.valueOf((int) value);  // Return it as an integer
        } else {
            return String.format("%.2f", value);  // Return the original string if it's not a whole number
        }
    }


	@Override
	public List<ErupiVoucherCreationRequestEntity> getErupiVoucherRequestByMgrId(Long mgrId) {
		List<EmployeeOnboardingEntity> employeeOnboading=new ArrayList<EmployeeOnboardingEntity>();
		List<ErupiVoucherCreationRequestEntity> finalList=new ArrayList<ErupiVoucherCreationRequestEntity>();
		try {
			employeeOnboading=employeeOnboardingDao.getEmployeeOnboardingManagerId(mgrId);
			for(EmployeeOnboardingEntity employeeOnboardingEntity:employeeOnboading) {
				List<ErupiVoucherCreationRequestEntity> list=erupiVoucherRequestDao.getVoucherCreationRequestEmp(employeeOnboardingEntity.getUserDetailsId());
				for (ErupiVoucherCreationRequestEntity employeeOnboardingEntity2 : list) {
					finalList.add(employeeOnboardingEntity2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalList;
	}


	@Override
	public EmployeeOnboardingManagerRequest updateErupiVoucherRequestUpdate(EmployeeOnboardingManagerRequest request) {
		// TODO Auto-generated method stub
				ErupiVoucherCreationRequestEntity erupiVoucherCreationRequestEntity=null;
				String response=MessageConstant.RESPONSE_FAILED;
				try {

					erupiVoucherCreationRequestEntity=new ErupiVoucherCreationRequestEntity();
					erupiVoucherCreationRequestEntity=erupiVoucherRequestDao.getVoucherCreationRequestEmpById(request.getId());
					if(erupiVoucherCreationRequestEntity!=null) {
						if(request.getStatus().equalsIgnoreCase("Approved")) {
							erupiVoucherCreationRequestEntity.setStatus(3);
							erupiVoucherCreationRequestEntity.setStatusMessage("Approved by manager");
							erupiVoucherCreationRequestEntity.setManagerRemarks("Approved by manager");
						}else if(request.getStatus().equalsIgnoreCase("Rejected")){
							erupiVoucherCreationRequestEntity.setStatus(2);
							erupiVoucherCreationRequestEntity.setStatusMessage("Rejected by manager");
							erupiVoucherCreationRequestEntity.setManagerRemarks(request.getRejecctRemark());
						}
						
						erupiVoucherCreationRequestEntity.setLoginuser(request.getLoginuser());
						erupiVoucherCreationRequestEntity.setApprejDate(LocalDateTime.now());
						
						erupiVoucherCreationRequestEntity=erupiVoucherRequestDao.saveDetails(erupiVoucherCreationRequestEntity);
						response=MessageConstant.RESPONSE_SUCCESS;
					}
					
					request.setResponse(response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return request;
	}


	@Override
	public List<ErupiVoucherCreationRequestEntity> getErupiVoucherRequestApprovedEmployerId(Long employerId,
			Long employeeId) {
		
		if(employerId!=null && employerId>0) {
			return erupiVoucherRequestDao.getVoucherCreationRequestApproved(employerId);
			}else {
				return erupiVoucherRequestDao.getVoucherCreationRequestApprovedEmp(employeeId);
			}
	}
	
}
