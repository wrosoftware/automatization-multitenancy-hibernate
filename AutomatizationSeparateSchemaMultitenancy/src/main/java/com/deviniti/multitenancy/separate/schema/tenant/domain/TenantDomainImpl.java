package com.deviniti.multitenancy.separate.schema.tenant.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.deviniti.multitenancy.separate.schema.tenant.TenantContext;
import com.deviniti.multitenancy.separate.schema.tenant.TenantDomain;
import com.deviniti.multitenancy.separate.schema.tenant.model.dto.TenantDTO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
class TenantDomainImpl implements TenantDomain {
	
	private final TenantService service;

	@Override
	public boolean setTenantInContext(String tenantId) {
		TenantDTO tenant = service.findByTenantId(tenantId);
		TenantContext.setCurrentTenant(tenant);
		return true;
	}

	@Override
	public void createNewTenant(TenantDTO dto) {
		service.createNewTenant(dto);
	}

	@Override
	public List<TenantDTO> getAllTenants() {
		return service.findAllTenants();
	}

}
