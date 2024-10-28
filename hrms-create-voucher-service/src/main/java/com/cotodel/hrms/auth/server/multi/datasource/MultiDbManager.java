package com.cotodel.hrms.auth.server.multi.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
@Configuration
@ConfigurationProperties(prefix = "tenants")
public class MultiDbManager {

	private final static ThreadLocal<String> currentDBConnection=new ThreadLocal<>();
	private final Map<Object,Object> dbSources= new ConcurrentHashMap<>();
	private final DataSourceProperties properties;

	private AbstractRoutingDataSource abstractRoutingDataSource;

	@Value("${default.datasource.url}")
	private String defaultDatasourceUrl;

	@Value("${default.datasource.username}")
	private String defaultDatasourceUserName;

	@Value("${default.datasource.password}")
	private String defaultDatasourcePassword;

	@Value("${default.datasource.schema}")
	private String defaultDatasourceSchema;


	public MultiDbManager(DataSourceProperties properties ) {

		this.properties=properties;
	}


	public Map<Object,Object> getDbSources(){

		return dbSources;
	}


	public void setDbSources (Map<String,Map<String,String>> dataSources) {

		dataSources.forEach((key,value)->dbSources.put(key, addTenant(value))  );
	}

	@Bean
	public DataSource dataSource() {

		abstractRoutingDataSource= new AbstractRoutingDataSource() {

			@Override
			protected Object determineCurrentLookupKey() {
				// TODO Auto-generated method stub
				return currentDBConnection.get();
			}
		};

		abstractRoutingDataSource.setTargetDataSources(dbSources);
		abstractRoutingDataSource.setDefaultTargetDataSource(defaultDataSource());
		abstractRoutingDataSource.afterPropertiesSet();

		return abstractRoutingDataSource;


	}


	private DataSource addTenant(Map<String, String> value) {
		HikariDataSource datasource= new HikariDataSource();
		datasource.setInitializationFailTimeout(0);
		datasource.setMaximumPoolSize(5);
		datasource.addDataSourceProperty("url", value.get("jdbcUrl"));
		datasource.addDataSourceProperty("user", value.get("username"));
		datasource.addDataSourceProperty("password", value.get("password"));
		datasource.addDataSourceProperty("schema", value.get("schema"));

		datasource.setDriverClassName(value.get("driverClassName"));

		datasource.setJdbcUrl(value.get("jdbcUrl"));
		datasource.setUsername(value.get("username"));
		datasource.setPassword(value.get("password"));
		datasource.setSchema(value.get("schema"));

		return datasource;
	}


	public DataSource defaultDataSource() {
		DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
		defaultDataSource.setDriverClassName("org.postgresql.Driver");
		defaultDataSource.setUrl(defaultDatasourceUrl);
		defaultDataSource.setUsername(defaultDatasourceUserName);
		defaultDataSource.setPassword(defaultDatasourcePassword);
		defaultDataSource.setSchema(defaultDatasourceSchema);

		return defaultDataSource;
	}

	public DataSourceProperties getProperties() {

		return properties;
	}

	
	public static void setCurrentDBConnection(String comId) {
		
		currentDBConnection.set(comId);
	}
}
