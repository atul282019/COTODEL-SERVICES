package com.cotodel.hrms.auth.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeOnboardingFailDao;
import com.cotodel.hrms.auth.server.dto.EmployeeOnboardingRequest;
import com.cotodel.hrms.auth.server.model.EmployeeOnboardingFailEntity;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.EmployeeOnboardingFailService;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeOnboardingServiceFailImpl implements EmployeeOnboardingFailService{

	@Autowired
	EmployeeOnboardingFailDao  employeeOnboardingFailDao;
	
	@Autowired
	ApplicationConstantConfig  applicationConstantConfig;
		
//	@Override
//	public EmployeeOnboardingRequest saveBulkFailEmployeeDetails(EmployeeOnboardingRequest request) {
//		
//		String response="";
//		try {
//			response = MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//			EmployeeOnboardingFailEntity employeeOnboarding = new EmployeeOnboardingFailEntity();
//			employeeOnboarding.setEmployerId(request.getEmployerId());
//			 ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	            ObjectOutputStream out = new ObjectOutputStream(bos);
//	            out.writeObject(request);
//	            byte[] byteData = bos.toByteArray();
//			employeeOnboarding.setFailValue(byteData);
//			employeeOnboarding = employeeOnboardingFailDao.saveDetails(employeeOnboarding);
//			response = MessageConstant.RESPONSE_SUCCESS;
//			request.setResponse(response);
//			
//		} catch (Exception e) {
//			response = MessageConstant.RESPONSE_FAILED;
//			request.setResponse(response);
//			//e.printStackTrace();
//		}
//		return request;
//
//	}

	@Override
	public List<EmployeeOnboardingRequest> getBulkFailDetailsList(Long employerid) {
		// TODO Auto-generated method stub
		List<EmployeeOnboardingRequest> employeeOnboardingRequests=new ArrayList<EmployeeOnboardingRequest>();
		List<EmployeeOnboardingFailEntity> employeeOnboardingFailEntities=new ArrayList<EmployeeOnboardingFailEntity>();
		employeeOnboardingFailEntities=employeeOnboardingFailDao.getBulkFailList(employerid);
		try {
		for (EmployeeOnboardingFailEntity employeeOnboardingFailEntity : employeeOnboardingFailEntities) {
		 System.out.println(employeeOnboardingFailEntity);
		
		
            byte[] byteData = employeeOnboardingFailEntity.getFailValue(); // This method retrieves your byte array

            ByteArrayInputStream bis = new ByteArrayInputStream(byteData);
            ObjectInputStream in = new ObjectInputStream(bis);
            Object object = in.readObject();

            if (object instanceof EmployeeOnboardingRequest) {
            	EmployeeOnboardingRequest employeeOnboardEntity = (EmployeeOnboardingRequest) object;
                System.out.println("Deserialized Person: " + employeeOnboardEntity);
               employeeOnboardingRequests.add(employeeOnboardEntity);
            }
		}
        } catch (Exception e) {
           // e.printStackTrace();
        }
		return employeeOnboardingRequests;
	}	
	
	
}
