package com.deviniti.multitenancy.separate.schema._configuration.flayway;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.cfg.Environment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deviniti.multitenancy.separate.schema.tenant.TenantContext;
import com.deviniti.multitenancy.separate.schema.tenant.TenantDomain;

@Configuration
class FlywayConfiguration {
	
	private static final String APPLICATION_PROPERTIES_PATH = "/application.properties";
	private static final String FLYWAY_URL_KEY = "flyway.url";
	private static final String FLYWAY_USER_KEY = "flyway.user";
	private static final String FLYWAY_PASSWORD_KEY = "flyway.password";
	private static final String FLYWAY_DRIVER_CLASS = "spring.datasource.driverClassName";
	
	DataSource dataSource;
	
	public FlywayConfiguration(DataSource dataSource) {
		this.dataSource = createFlywayDataSource();
	}

	@Bean
	FlywayBuilder flywayBuilder() {
		return new FlywayBuilder(dataSource);
	}

	@Bean
	Flyway flyway() {
		Flyway flyway = flywayBuilder().createFlyway(TenantContext.DEFAULT_TENANT_ID);
		flyway.migrate();
		return flyway;
	}
	
	@Bean
    CommandLineRunner commandLineRunner(TenantDomain tenantDomain, DataSource dataSource) {
        return args -> {
            tenantDomain.getAllTenants().forEach(tenant -> {
            	Flyway flyway = flywayBuilder().createFlyway(tenant);
        		flyway.migrate();
            });
        };
    }
	
	private DataSource createFlywayDataSource() {
		Properties properties = getPropertiesForFlyway();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(properties.getProperty(FLYWAY_DRIVER_CLASS));
		dataSource.setUrl(properties.getProperty(FLYWAY_URL_KEY));
		dataSource.setUsername(properties.getProperty(FLYWAY_USER_KEY));
		dataSource.setPassword(properties.getProperty(FLYWAY_PASSWORD_KEY));
		return dataSource;
	}
	
	private Properties getPropertiesForFlyway() {
        try {
        	Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream(APPLICATION_PROPERTIES_PATH));
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("Cannot open hibernate properties: "+ APPLICATION_PROPERTIES_PATH);
		}
	}
}
