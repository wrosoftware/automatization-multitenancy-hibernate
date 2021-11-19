package com.deviniti.multitenancy.separate.schema._configuration.hibernate.multitenancy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.stereotype.Component;

import com.deviniti.multitenancy.separate.schema.tenant.TenantContext;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.DataSourceDTO;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

@SuppressWarnings("serial")
@Component
public class SchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {
	
	private static final String HIBERNATE_PROPERTIES_PATH = "/application.properties";
	private static final String DEFAULT_SCHEMA_NAME = "public";
	private final Map<String, ConnectionProvider> connectionProviderMap;

	public SchemaMultiTenantConnectionProvider() {
		this.connectionProviderMap = new HashMap<String, ConnectionProvider>();
	}
	
	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		Connection connection = super.getConnection(tenantIdentifier);
		connection.createStatement().execute(String.format("SET SCHEMA '%s';", getTenantSchema()));
		return connection;
	}
	
	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return getConnectionProvider(TenantContext.DEFAULT_TENANT_ID);
	}

	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		return getConnectionProvider(tenantIdentifier);
	}
	
	private String getTenantSchema() {
		return Optional.ofNullable(TenantContext.getCurrentTenant())
				.map(TenantDTO::getDataSource)
				.map(DataSourceDTO::getSchemaName)
				.orElse(DEFAULT_SCHEMA_NAME);
	}
	
	private ConnectionProvider getConnectionProvider(String tenantIdentifier) {
		return Optional.ofNullable(tenantIdentifier)
				.map(connectionProviderMap::get)
				.orElseGet(() -> createNewConnectionProvider(tenantIdentifier));
	}

	private ConnectionProvider createNewConnectionProvider(String tenantIdentifier) {
		return Optional.ofNullable(tenantIdentifier)
				.map(this::createConnectionProvider)
				.map(connectionProvider -> {
					connectionProviderMap.put(tenantIdentifier, connectionProvider);
					return connectionProvider;
				})
				.orElseThrow(() -> new ConnectionProviderException("Cannot create new connection provider for tenant: "+tenantIdentifier));
	}
	
	private ConnectionProvider createConnectionProvider(String tenantIdentifier) {
		return Optional.ofNullable(tenantIdentifier)
				.map(this::getHibernatePropertiesForTenantId)
				.map(this::initConnectionProvider)
				.orElse(null);
	}
	
	private Properties getHibernatePropertiesForTenantId(String tenantId) {
        try {
        	Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream(HIBERNATE_PROPERTIES_PATH));
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("Cannot open hibernate properties: "+ HIBERNATE_PROPERTIES_PATH);
		}
	}

	private ConnectionProvider initConnectionProvider(Properties hibernateProperties) {
		DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
		connectionProvider.configure(hibernateProperties);
		return connectionProvider;
	}
	
}
