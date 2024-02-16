package com.cotodel.hrms.auth.server;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableCaching
@EntityScan( basePackages = {"com.cotodel.hrms.auth.*"})
@ComponentScan(basePackages = {"com.cotodel.hrms.auth.*"})
@EnableJpaRepositories("com.cotodel.hrms.auth.*")
@EnableTransactionManagement(proxyTargetClass=true)
@SpringBootApplication(scanBasePackages="com.cotodel.hrms.auth.*", exclude = { SecurityAutoConfiguration.class })
public class OtpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpServerApplication.class, args);
	}

}
