package com.deviniti.multitenancy.separate.schema.configuration.hibernate.multitenancy;

import java.util.Optional;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import com.deviniti.multitenancy.separate.schema.tenant.TenantContext;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		return getTenantIdentifier()
				.orElse(TenantContext.DEFAULT_TENANT_ID);
	}

	private Optional<String> getTenantIdentifier() {
		return Optional.ofNullable(TenantContext.getCurrentTenant())
				.map(TenantDTO::getTenantId);
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
