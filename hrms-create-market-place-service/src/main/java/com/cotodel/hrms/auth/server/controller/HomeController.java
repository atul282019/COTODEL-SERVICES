package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.service.ReputeService;

@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
	@Autowired
	ReputeService reputeService;

    @GetMapping("/")
    public String home(@RequestParam String code,
            @RequestParam String vault_url,
            @RequestParam String hrms_id,
            @RequestParam String hrms_name,
            @RequestParam String hrms_logo_url,
            @RequestParam String company_id,
            @RequestParam String role) {
    	System.out.println("homepage"+code);
    	System.out.println("homepage"+vault_url);
    	System.out.println("homepage"+hrms_id);
    	System.out.println("homepage"+hrms_name);
    	System.out.println("homepage"+hrms_logo_url);
    	System.out.println("homepage"+company_id);
    	logger.info("inside Get HomeController...respString.");
    	reputeService.getReputeToken(code,vault_url);
        return "Welcome to the homepage!";
    }
    
    @PostMapping("/")
    public String homePost(@RequestBody String respString) {
    	System.out.println("homepage");
    	logger.info("inside Post HomeController...respString."+respString);
        return "Welcome to the homepage!";
    }
    
}