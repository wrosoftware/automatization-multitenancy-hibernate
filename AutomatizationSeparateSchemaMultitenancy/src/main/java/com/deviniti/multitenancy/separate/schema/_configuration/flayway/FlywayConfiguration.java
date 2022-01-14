package com.deviniti.multitenancy.separate.schema._configuration.flayway;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deviniti.multitenancy.separate.schema.tenant.TenantContext;
import com.deviniti.multitenancy.separate.schema.tenant.TenantDomain;

@Configuration
class FlywayConfiguration {
	
	private final String flywayUrl;
	private final String flywayUser;
	private final String flywayPassword;
	private final String flywayDriver;
	
	DataSource dataSource;
	
	public FlywayConfiguration(DataSource dataSource,
			@Value("${flyway.url}") String flywayUrl,
			@Value("${flyway.user}") String flywayUser,
			@Value("${flyway.password}") String flywayPassword,
			@Value("${spring.datasource.driverClassName}") String flywayDriver) {
		this.flywayUrl = flywayUrl;
		this.flywayUser = flywayUser;
		this.flywayPassword = flywayPassword;
		this.flywayDriver = flywayDriver;
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
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(flywayDriver);
		dataSource.setUrl(flywayUrl);
		dataSource.setUsername(flywayUser);
		dataSource.setPassword(flywayPassword);
		return dataSource;
	}
	
}
