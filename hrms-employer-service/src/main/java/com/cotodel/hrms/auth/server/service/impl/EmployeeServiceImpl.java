package com.cotodel.hrms.auth.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cotodel.hrms.auth.server.dao.EmployeeDao;
import com.cotodel.hrms.auth.server.dto.Employee;
import com.cotodel.hrms.auth.server.dto.EmployeeRequest;
import com.cotodel.hrms.auth.server.model.EmployeeEntity;
import com.cotodel.hrms.auth.server.model.EmployerEntity;
import com.cotodel.hrms.auth.server.service.EmployeeService;
import com.cotodel.hrms.auth.server.util.CopyUtility;
import com.cotodel.hrms.auth.server.util.MessageConstant;
@Repository
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeDao  employeeDao;
	
	@Override
	public EmployeeRequest saveEmployeeDetails(EmployeeRequest request) {
		
		EmployerEntity employerEntity=null;
		String response="";
		try {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);		
			EmployeeEntity employee=new EmployeeEntity();
			CopyUtility.copyProperties(request,employee);
			employee.setEmployer(request.getEmployer());
			//employee.setPan(user.getPan());
		
			employee=employeeDao.saveDetails(employee);
			//user.setEmployeeId(employee.getEmployeeId());
			response=MessageConstant.RESPONSE_SUCCESS;
			request.setResponse(response);
		} catch (Exception e) {
			response=MessageConstant.RESPONSE_FAILED;
			request.setResponse(response);
		}
		return request;

	}

	@Override
	public EmployeeRequest getEmployeeDetails(Long empid) {
		// TODO Auto-generated method stub
		EmployeeEntity employee=new EmployeeEntity();
		EmployerEntity employer=new EmployerEntity();
		EmployeeRequest request=new EmployeeRequest();
		employee=employeeDao.getUser(empid);
		CopyUtility.copyProperties(employee,request);
		
		//request.set
		//System.out.println(employee.toString());
		return request;
	}

	@Override
	public List<Employee> getEmployeeDetailsList() {
		List<EmployeeEntity> employee=null;
		List<Employee> employeeList=new ArrayList<Employee>();
		List<EmployeeEntity> employeeList1=new ArrayList<EmployeeEntity>();
		employee=employeeDao.getEmployee();
		Employee employee3=new Employee();
		for (EmployeeEntity employee2 : employee) {
			//employee3=null;
			//CopyUtility.copyProperties(employee2,employee3);
			employee3=getValue(employee2);
			employeeList.add(employee3);
			//employeeList1.add(employee2);
		}
		return employeeList;
	}
	
	public Employee getValue(EmployeeEntity emp) {
		Employee employee2=new Employee();
		employee2.setEmployeeId(emp.getEmployeeId()!=null?emp.getEmployeeId():0);
		//employee2.setEmployer_id(employeeEntity.getEmployer().getEmployerId()!=null?);
		employee2.setFirstName(emp.getFirstName()!=null?emp.getFirstName():"");
		employee2.setLastName(emp.getLastName()!=null?emp.getLastName():"");
		employee2.setDateOfBirth(emp.getDateOfBirth());
		employee2.setGender(emp.getGender()!=null?emp.getGender():"");
		employee2.setMobile(emp.getMobile()!=null?emp.getMobile():"");
		employee2.setPicUrl(emp.getPicUrl()!=null?emp.getPicUrl():"");
		employee2.setEmail(emp.getEmail()!=null?emp.getEmail():"");
		employee2.setAddress(emp.getAddress()!=null?emp.getAddress():"");
		employee2.setRoleId(emp.getRoleId()!=null?emp.getRoleId():0);
		employee2.setUsername(emp.getUsername()!=null?emp.getUsername():"");
		employee2.setPwd(emp.getPwd()!=null?emp.getPwd():"");
		employee2.setBankAccount(emp.getBankAccount()!=null?emp.getBankAccount():"");
		employee2.setIfsc(emp.getIfsc()!=null?emp.getIfsc():"");
		employee2.setUrn(emp.getUrn()!=null?emp.getUrn():"");
		employee2.setPan(emp.getPan()!=null?emp.getPan():"");
		employee2.setAadhaar(emp.getAadhaar()!=null?emp.getAadhaar():"");
		employee2.setExtra1(emp.getExtra1()!=null?emp.getExtra1():"");
		employee2.setExtra2(emp.getExtra2()!=null?emp.getExtra2():"");
		employee2.setStatus(emp.isStatus());
		employee2.setIntextra1(emp.getIntextra1());
		return employee2;
	}
	

	

}
