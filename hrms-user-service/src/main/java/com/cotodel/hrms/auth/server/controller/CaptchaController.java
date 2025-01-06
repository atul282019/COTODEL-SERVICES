package com.cotodel.hrms.auth.server.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.auth.server.exception.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/Api")
public class CaptchaController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserSignUpController.class);
	
	
	
	public static final String FILE_TYPE = "jpeg";

	@Operation(summary = "This API will provide the Authentication token", security = {
    		@SecurityRequirement(name = "task_auth")}, tags = {"Authentication Token APIs"})
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200",description = "ok", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ResponseEntity.class))),		
    @ApiResponse(responseCode = "400",description = "Request Parameter's Validation Failed", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404",description = "Request Resource was not found", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",description = "System down/Unhandled Exceptions", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))})
    @RequestMapping(value = "/get/captcha",produces = {"application/json"}, consumes = {"application/json","application/text"},method = RequestMethod.GET)
    public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response,ModelMap model){
	       
			response.setHeader( "Cache-Control", "no-store" );
			response.setHeader( "Pragma", "no-cache" );
			response.setDateHeader( "Expires", 0 );
	       
	       String captchaStr=generateCaptcha(6);
	       try {
	           int width=125;      
	           int height=35;

	           Color bg = new Color(44,45,110);
	           Color fg = new Color(255,255,255);
	           
	           Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
	           attributes.put(TextAttribute.TRACKING, 0.4);

	           Font font = new Font("Arial", Font.BOLD, 16);
	         
	           font = font.deriveFont(attributes);
	           
	           BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
	           Graphics g = cpimg.createGraphics();

	           g.setFont(font);
	           g.setColor(bg);
	           g.fillRect(0, 0, width, height);
	           g.setColor(fg);
	           g.drawString(captchaStr,10,25);   

	           HttpSession session = request.getSession(true);
	           session.setAttribute("CAPTCHA", captchaStr);

	           logger.info("captchaStr==="+captchaStr);
	          OutputStream outputStream = response.getOutputStream();

	          ImageIO.write(cpimg, FILE_TYPE, outputStream);
	        
	          outputStream.flush();
	          outputStream.close();

	          
	       } catch (Exception e) {
	               e.printStackTrace();
	       }
	   }

	   private String generateCaptcha(int captchaLength) {

		   String saltChars = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789";
		   StringBuffer captchaStrBuffer = new StringBuffer();
		   Random rnd = new Random();

		   while (captchaStrBuffer.length() < captchaLength)
		   {
			   int index = (int) (rnd.nextFloat() * saltChars.length());
			   captchaStrBuffer.append(saltChars.substring(index, index+1));
		   }

		   return captchaStrBuffer.toString();

	}

		
}
