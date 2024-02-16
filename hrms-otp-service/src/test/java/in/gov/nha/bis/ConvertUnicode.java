package in.gov.nha.bis;

import java.nio.charset.StandardCharsets;

import org.springframework.web.util.UriUtils;

public class ConvertUnicode {

	public static void main(String[] args) {
		
		System.out.println(UriUtils.encode("You have been successfully verified for Ayushman Bharat PM-JAY. Your reference no. is {#var#}. The process of Ayushman card creation is absolutely free.", StandardCharsets.UTF_8));
	}
	
	
}
