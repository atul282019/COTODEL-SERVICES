package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cotodel.hrms.auth.server.properties.ApplicationConstantConfig;
import com.cotodel.hrms.auth.server.service.OcrReaderService;

@RestController
@RequestMapping("/Api")
public class CronJobController {
	
	private static final Logger logger = LoggerFactory.getLogger(CronJobController.class);
	
	@Autowired
	OcrReaderService ocrReaderService;
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	

	    
	    @Scheduled(cron = "0 0 */2 * * *")
	    public void runEveryTwoHours() {
	        System.out.println("Running every 2 hours: " + java.time.LocalDateTime.now());
	    }

	}
