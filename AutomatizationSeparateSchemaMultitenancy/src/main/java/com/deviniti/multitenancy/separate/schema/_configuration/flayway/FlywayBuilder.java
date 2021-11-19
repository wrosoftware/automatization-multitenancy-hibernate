package com.deviniti.multitenancy.separate.schema._configuration.flayway;

import java.util.Optional;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

import com.deviniti.multitenancy.separate.schema.tenant.model.dto.DataSourceDTO;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FlywayBuilder {
	
	private static final String DEFAULT_SCHEMA_LOCATION = "db/migration/default";
	private static final String TENANT_SCHEMA_LOCATION = "db/migration/tenants";
	
	private final DataSource dataSource;

	Flyway createFlyway(String schemaName) {
		return Flyway.configure()
			.dataSource(dataSource)
			.locations(DEFAULT_SCHEMA_LOCATION)
			.schemas(schemaName)
			.load();
	}

	public Flyway createFlyway(TenantDTO tenant) {
		return Flyway.configure()
				.dataSource(dataSource)
				.locations(TENANT_SCHEMA_LOCATION)
				.schemas(getSchemaName(tenant))
				.load();
	}

	private String getSchemaName(TenantDTO tenant) {
		return Optional.ofNullable(tenant)
				.map(TenantDTO::getDataSource)
				.map(DataSourceDTO::getSchemaName)
				.orElseThrow(() -> new RuntimeException("tenant model without schema name"));
	}
	
}
