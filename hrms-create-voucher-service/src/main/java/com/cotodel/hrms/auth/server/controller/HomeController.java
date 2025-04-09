package com.cotodel.hrms.auth.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.service.ErupiVoucherRedemService;

@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	ErupiVoucherRedemService erupiVoucherRedemService;
	
    @GetMapping("/")
    public String home(@RequestBody String respString) {
    	System.out.println("homepage");
    	logger.info("inside Get HomeController...respString."+respString);
    	String result=erupiVoucherRedemService.calApiErupiVoucherRedemDetails(respString);
        return "Welcome to the homepage!";
    }
    
    @PostMapping("/")
    public String homePost(@RequestBody String respString) {
    	//System.out.println("homepage");
    	logger.info("inside Post HomeController...respString."+respString);
    	String result=erupiVoucherRedemService.calApiErupiVoucherRedemDetails(respString);
        return "Welcome to the homepage!";
    }
    
    @GetMapping("/callback/erupiprod/")
    public String homeProd(@RequestBody String respString) {
    	System.out.println("homepage");
    	logger.info("inside Geterupiprod HomeController...respString."+respString);
    	String result=erupiVoucherRedemService.calApiErupiVoucherRedemDetails(respString);
        return "Welcome to the homepage!";
    }
    
    @PostMapping("/callback/erupiprod/")
    public String homePostProd(@RequestBody String respString) {
    	//System.out.println("homepage");
    	logger.info("inside Post erupiprod HomeController...respString."+respString);
    	String result=erupiVoucherRedemService.calApiErupiVoucherRedemDetails(respString);
        return "Welcome to the homepage!";
    }
    
}