package com.deviniti.multitenancy.separate.schema.tenant.domain;

import org.springframework.stereotype.Component;

import com.deviniti.multitenancy.separate.schema.tenant.TenantContext;
import com.deviniti.multitenancy.separate.schema.tenant.TenantDomain;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class TenantDomainImpl implements TenantDomain {
	

	@Override
	public boolean setTenantInContext(String tenantId) {
		TenantDTO tenant = null;
		TenantContext.setCurrentTenant(tenant);
		return true;
	}

}
