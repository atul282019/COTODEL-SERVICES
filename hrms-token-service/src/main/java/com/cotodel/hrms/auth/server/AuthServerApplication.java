package com.cotodel.hrms.auth.server;


/**
 * @author vinay
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@SpringBootApplication(scanBasePackages="com.cotodel.hrms.auth.*", exclude = { SecurityAutoConfiguration.class })
@EnableCaching
@EnableScheduling
@EnableJpaRepositories("com.cotodel.hrms.auth.*")
@EnableTransactionManagement(proxyTargetClass=true)
@ComponentScan(basePackages = "com.cotodel.hrms.auth.*")
@OpenAPIDefinition( servers=  { @Server(url="/hrmsAuthSevice", description = "Default Server URL" )})
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	 public OpenAPI customOpenAPI(@Value("${application-description}") String appDescription,
			 @Value("${application-version}") String appVersion ) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("HRMS Authentication Service API")
                		.description(
                        "This is a HRMS service using springdoc-openapi and OpenAPI 3.")
                		.license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
