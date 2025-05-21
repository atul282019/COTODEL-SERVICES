package com.cotodel.hrms.auth.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.DirectorOnboardingDao;
import com.cotodel.hrms.auth.server.dto.DirectorOnboardingRequest;
import com.cotodel.hrms.auth.server.model.DirectorOnboardingEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.DirectorOnboardingService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
import com.cotodel.hrms.auth.server.util.ValidateConstants;
@Repository
public class DirectorOnboardingServiceImpl implements DirectorOnboardingService{

	@Autowired
	DirectorOnboardingDao  directorOnboardingDao;
	
	@Autowired
	ApplicationConstantConfig  applicationConstantConfig;

	

	@Override
	public DirectorOnboardingRequest saveDirectorDetails(DirectorOnboardingRequest request) {
		String response="";
		DirectorOnboardingEntity directorOnboardingEntity=null;
		
		try {
			String message=validateDirectorOnboardingRequest(request);
			if(message!=null && !message.equalsIgnoreCase("")) {
				request.setResponse(message);
				return request;
			}
//			String errorMessage =SQLInjectionValidator.validateFieldsForSQLInjection(request);
//	        if (errorMessage != null) {
//	        	request.setResponse(errorMessage);
//				return request;
//	        }
			 String dataString = request.getOrgId()+request.getName()+request.getEmail()+request.getMobile()+request.getCreatedby()+request.getClientKey()+MessageConstant.SECRET_KEY;;
			//logger.info("dataString::"+dataString);
			String hash=ValidateConstants.generateHash(dataString);
			//logger.info("hash::"+hash);
			if(!request.getHash().equalsIgnoreCase(hash)) {
				request.setResponse(MessageConstant.HASH_ERROR);
				return request;
			}
			response=MessageConstant.RESPONSE_FAILED;			
			request.setResponse(response);
			directorOnboardingEntity=new DirectorOnboardingEntity();
			CopyUtility.copyProperties(request,directorOnboardingEntity);
			directorOnboardingEntity.setStatus(1);
			directorOnboardingEntity.setCreationDate(LocalDateTime.now());
			directorOnboardingDao.saveDetails(directorOnboardingEntity);
			request.setResponse(MessageConstant.RESPONSE_SUCCESS);
		}catch (DataIntegrityViolationException ex) {
			request.setResponse(MessageConstant.DIRECT_UNIQUE);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}
	
	private String validateDirectorOnboardingRequest(DirectorOnboardingRequest directorOnboardingRequest) {
        // Check for required fields and validate the data
        String message="";
               
        
        message=ValidateConstants.validateOrganizationId(directorOnboardingRequest.getOrgId());
        if(message!=null)
        	return message;
        message=ValidateConstants.validateMandatoryName(directorOnboardingRequest.getName());
        if(message!=null)
        	return message;
       message=ValidateConstants.validateEmail(directorOnboardingRequest.getEmail());
       if(message!=null)
       	return message;
       message=ValidateConstants.validateMobile(directorOnboardingRequest.getMobile());
       if(message!=null)
       	return message;
       message=ValidateConstants.validateDin(directorOnboardingRequest.getDin());
       if(message!=null)
       	return message;
       message=ValidateConstants.validateDesignation(directorOnboardingRequest.getDesignation());
       if(message!=null)
       	return message;
       message=ValidateConstants.validateAddress(directorOnboardingRequest.getAddress());
       if(message!=null)
       	return message;
       message=ValidateConstants.validateCreatedBy(directorOnboardingRequest.getCreatedby());
       
        return message;
    }

	@Override
	public List<DirectorOnboardingEntity> getDirectorDetailsList(Long employerid) {
		// TODO Auto-generated method stub
		return directorOnboardingDao.getEmployeeOnboardingList(employerid);
	}

}
