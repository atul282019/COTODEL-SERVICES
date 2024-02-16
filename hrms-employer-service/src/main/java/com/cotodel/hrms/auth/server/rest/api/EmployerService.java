package com.cotodel.hrms.auth.server.rest.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.auth.server.config.RestTemplateConfig;
import com.cotodel.hrms.auth.server.dto.DbMaster;

@Service
public class EmployerService {
	
	
	@Value(value ="${employer.db.master}")
	private String dbMasterUrl;
	
	@Autowired
	RestTemplateConfig restTemplateConfig;
	
	public DbMaster getEmployerDb() {
		
		HttpHeaders header= new HttpHeaders();
		header.add("Content-Type", "application/json");
		header.add("Accept", "application/json");
		
		HttpEntity requestEntity= new HttpEntity<String>(header);
		
		
		ResponseEntity<DbMaster> responseEntity = new RestTemplate()
				.exchange(dbMasterUrl,HttpMethod.GET,requestEntity,DbMaster.class);
		
		return responseEntity.getBody();

	}

}
